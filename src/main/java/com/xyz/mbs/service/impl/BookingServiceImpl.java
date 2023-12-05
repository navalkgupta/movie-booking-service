package com.xyz.mbs.service.impl;

import com.xyz.mbs.enums.BookingStatus;
import com.xyz.mbs.enums.PaymentStatus;
import com.xyz.mbs.enums.SeatStatus;
import com.xyz.mbs.exceptions.ChosenSeatsNotAvailableException;
import com.xyz.mbs.exceptions.RecordNotFoundException;
import com.xyz.mbs.model.*;
import com.xyz.mbs.model.dto.BookingDto;
import com.xyz.mbs.repository.*;
import com.xyz.mbs.service.BookingService;
import com.xyz.mbs.service.PaymentService;
import com.xyz.mbs.service.QueueService;
import com.xyz.mbs.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private QueueService queueService;

    private static final double CANCEL_CHARGE = 15.0;

    @Transactional
    public Booking createBooking(BookingDto bookingDto){
        Booking booking = new Booking();
        booking.setCustomer((Customer) userService.getUser(bookingDto.getUserId()));
        booking.setShow(showRepository.findById(bookingDto.getShowId()).orElseThrow(RecordNotFoundException::new));
        List<Long> seatIds = bookingDto.getSeatIds();
        Set<ShowSeat> showSeats = new HashSet<>(showSeatRepository.getSeatsByIds(seatIds));
        booking.setShowSeats(showSeats);
        checkAndUpdateSeatsForBooking(seatIds, SeatStatus.SELECTED);
        Payment payment = paymentService.makePayment(createPayment(booking, bookingDto));
        if(payment.getPaymentStatus().equals(PaymentStatus.SUCCESS)){
            checkAndUpdateSeatsForBooking(seatIds, SeatStatus.BOOKED);
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            queueService.saveToNotify("Movie ticket booked with booking id: "+booking.getId());
        }else{
            showSeatRepository.updateSeatsByIds(seatIds, SeatStatus.EMPTY);
            booking.setBookingStatus(BookingStatus.FAILED);
            queueService.saveToNotify("Movie ticket could not be booked with booking id: "+booking.getId());
        }
        booking.setPayment(payment);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(long bookingId) {
        Booking booking = getBooking(bookingId);
        Refund refund = paymentService.initiateRefund(booking.getPayment(), calculateRefundAmount(booking.getPayment().getAmount()));
        if(refund.getPaymentStatus().equals(PaymentStatus.SUCCESS)){
            showSeatRepository.updateSeatsByIds(booking.getShowSeats().stream().map(ShowSeat::getId).toList(), SeatStatus.EMPTY);
            booking.setBookingStatus(BookingStatus.CANCELLED);
            booking.setRefund(refund);
            queueService.saveToNotify("Movie ticket cancelled with booking id: "+booking.getId());
            return bookingRepository.save(booking);
        }else{
            queueService.saveToNotify("Movie ticket could not be cancelled for booking id: "+booking.getId());
            return booking;
        }
    }

    public Booking getBooking(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(RecordNotFoundException::new);
    }

    public List<Booking> getBookings(long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    private void checkAndUpdateSeatsForBooking(List<Long> seatIds, SeatStatus seatStatus){
        if(showSeatRepository.getSeatStatusByIds(seatIds).stream().noneMatch(status -> status.equals(SeatStatus.BOOKED))){
            showSeatRepository.updateSeatsByIds(seatIds, seatStatus);
        }else{
            throw new ChosenSeatsNotAvailableException();
        }
    }

    private Payment createPayment(Booking booking, BookingDto bookingDto){
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentMode(bookingDto.getPaymentMode());
        payment.setAmount(calculateBookingAmount(booking.getShowSeats()));
        return payment;
    }

    public double calculateBookingAmount(Set<ShowSeat> showSeats){
        double price = showSeats.stream().findFirst().orElseThrow(RecordNotFoundException::new).getBooking().getShow().getPrice();
        return price*showSeats.size();
    }

    public double calculateRefundAmount(double bookingAmount){
        return bookingAmount*(100-CANCEL_CHARGE)/100;
    }
}

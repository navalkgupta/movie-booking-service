package com.xyz.mbs.service;

import com.xyz.mbs.model.Booking;
import com.xyz.mbs.model.Payment;
import com.xyz.mbs.model.dto.BookingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    Booking getBooking(long bookingId);

    Booking createBooking(BookingDto bookingDto);

    Booking cancelBooking(long bookingId);

    List<Booking> getBookings(long userId);
}

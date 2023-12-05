package com.xyz.mbs.controller;

import com.xyz.mbs.exceptions.ChosenSeatsNotAvailableException;
import com.xyz.mbs.exceptions.RecordNotFoundException;
import com.xyz.mbs.model.dto.BookingDto;
import com.xyz.mbs.service.BookingService;
import com.xyz.mbs.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping(path = "/v1")
@RestController
public class BookingController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/shows")
    public ResponseEntity<?> getShows(@RequestParam long cityId, @RequestParam long movieId,
                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return ResponseEntity.ok(searchService.getShows(cityId, movieId, date));
    }

    @PostMapping("/availableSeats")
    public ResponseEntity<?> getAvailableShowSeats(@RequestParam long showId){
        return ResponseEntity.ok(searchService.getEmptyShowSeats(showId));
    }

    @PostMapping("/bookShow")
    public ResponseEntity<?> createBookingShow(@RequestBody BookingDto bookingDto){
        return ResponseEntity.ok(bookingService.createBooking(bookingDto));
    }

    @GetMapping("/getBookShow")
    public ResponseEntity<?> getBookingShow(@RequestParam long bookingId){
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @GetMapping("/getBookShows")
    public ResponseEntity<?> getBookingShows(@RequestParam long customerId){
        return ResponseEntity.ok(bookingService.getBookings(customerId));
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestParam long bookingId){
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Record not found for given id")
    @ExceptionHandler(RecordNotFoundException.class)
    void recordNotFound(RecordNotFoundException recordNotFoundException){}

    @ResponseStatus(value = HttpStatus.LOCKED, reason = "Chosen seats not available for booking")
    @ExceptionHandler(ChosenSeatsNotAvailableException.class)
    void chosenSeatsLocked(ChosenSeatsNotAvailableException chosenSeatsNotAvailableException){}
}

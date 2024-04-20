package com.jiayi.hotelbooking.controller;


import com.jiayi.hotelbooking.dto.BookingRequest;
import com.jiayi.hotelbooking.dto.BookingResponse;
import com.jiayi.hotelbooking.model.Booking;
import com.jiayi.hotelbooking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/request")
    public String createBooking(@RequestBody BookingRequest bookingRequest) {
        bookingService.createBooking(bookingRequest);
        return "The booking is created";
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> checkBooking(@PathVariable Long bookingId) {
        BookingResponse bookingResponse = bookingService.checkBooking(bookingId);
        return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{hotelId}")
    public List<BookingResponse> checkBookingByRoomId(@PathVariable Long roomId){
        return bookingService.checkBookingByRoomId(roomId);
    }


}

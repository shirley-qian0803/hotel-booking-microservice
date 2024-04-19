package com.jiayi.hotelbooking.service;

import com.jiayi.hotelbooking.dto.BookingRequest;
import com.jiayi.hotelbooking.dto.BookingResponse;
import com.jiayi.hotelbooking.model.Booking;
import com.jiayi.hotelbooking.model.Status;
import com.jiayi.hotelbooking.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    public void createBooking(BookingRequest bookingRequest) {
        Booking booking = Booking
                .builder()
                .userId(bookingRequest.getUserId())
                .hotelId(bookingRequest.getHotelId())
                .roomId(bookingRequest.getRoomId())
                .status(Status.PENDING)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        if (isStock(bookingRequest.getRoomId())){
            bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later.");
        }
    }

    private boolean isStock(Long roomId) {
        return true;
    }

    public BookingResponse checkBooking(Long bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId).orElseThrow();
        return BookingResponse
                .builder()
                .userId(booking.getUserId())
                .roomId(booking.getRoomId())
                .hotelId(booking.getHotelId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .status(booking.getStatus())
                .build();
    }
}

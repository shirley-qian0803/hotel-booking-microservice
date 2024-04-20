package com.jiayi.hotelbooking.service;

import com.jiayi.hotelbooking.dto.BookingRequest;
import com.jiayi.hotelbooking.dto.BookingResponse;
import com.jiayi.hotelbooking.model.Booking;
import com.jiayi.hotelbooking.model.Status;
import com.jiayi.hotelbooking.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    public void createBooking(BookingRequest bookingRequest) {
        // check whether this is a valid userId, hotelId, roomId; whether room of that roomId has Hotel
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
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate());
        boolean isAvailable = conflictingBookings.isEmpty();

        if (isAvailable){
            bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later.");
        }
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

    public List<BookingResponse> checkBookingByRoomId(Long roomId) {
        // find all the room with the desired roomId
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        // Steam and map it to bookingResponse (with hotelId, roomId etc.)
        return bookings.stream().map(this::mapToBookingResponse).toList();
    }

    private BookingResponse mapToBookingResponse(Booking booking){
        return BookingResponse
                .builder()
                .userId(booking.getUserId())
                .hotelId(booking.getHotelId())
                .roomId(booking.getRoomId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .build();
    }
}

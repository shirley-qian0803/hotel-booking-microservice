package com.jiayi.hotelbooking.repository;

import com.jiayi.hotelbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingId(Long bookingId);
    List<Booking> findByRoomId(Long roomId);

    @Query("SELECT b FROM Booking b WHERE b.roomId = :roomId AND b.checkInDate < :end AND b.checkOutDate > :start")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId, @Param("start") Timestamp start, @Param("end") Timestamp end);
}

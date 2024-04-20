package com.jiayi.hotelmanagement.repository;

import com.jiayi.hotelmanagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByHotelEmail(String hotelEmail);

}

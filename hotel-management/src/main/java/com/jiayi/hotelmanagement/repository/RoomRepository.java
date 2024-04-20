package com.jiayi.hotelmanagement.repository;

import com.jiayi.hotelmanagement.model.Hotel;
import com.jiayi.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByHotelAndRoomName(Hotel hotel, String roomName);
}

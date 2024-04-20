package com.jiayi.hotelmanagement.service;

import com.jiayi.hotelmanagement.dto.BookingResponse;
import com.jiayi.hotelmanagement.dto.RoomRequest;
import com.jiayi.hotelmanagement.dto.RoomResponse;
import com.jiayi.hotelmanagement.dto.UpdateRequest;
import com.jiayi.hotelmanagement.model.Hotel;
import com.jiayi.hotelmanagement.model.Room;
import com.jiayi.hotelmanagement.repository.HotelRepository;
import com.jiayi.hotelmanagement.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final WebClient.Builder webClientBuilder;

    public RoomResponse createRoom(Long hotelId, RoomRequest roomRequest) {
        // find the hotel
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));

        // check whether the room is created before
        if (roomRepository.findByHotelAndRoomName(hotel, roomRequest.getRoomName()).isPresent()){
            throw new IllegalArgumentException("The room is already created");
        }

        // create the room
        Room room = Room
                .builder()
                .roomPrice(roomRequest.getRoomPrice())
                .roomType(roomRequest.getRoomType())
                .roomStatus(roomRequest.getRoomStatus())
                .roomName(roomRequest.getRoomName())
                .hotel(hotel)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();

        // change the hotel's lists of room
        hotel.getRooms().add(room);

        // store the room
        roomRepository.save(room);
        hotelRepository.save(hotel);

        return mapRoomToRoomResponse(room);
    }

    private RoomResponse mapRoomToRoomResponse(Room room) {
        return RoomResponse
                .builder()
                .roomName(room.getRoomName())
                .roomType(room.getRoomType())
                .roomStatus(room.getRoomStatus())
                .roomPrice(room.getRoomPrice())
                .roomId(room.getRoomId())
                .hotelId(room.getHotel().getHotelId())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }

    public RoomResponse updateRoom(Long hotelId, Long roomId, UpdateRequest updateRequest) {
        // Find the hotel and the room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Update the room details as per the update request
        if (updateRequest.getRoomPrice() != null) {
            room.setRoomPrice(updateRequest.getRoomPrice());
        }
        if (updateRequest.getRoomType() != null) {
            room.setRoomType(updateRequest.getRoomType());
        }
        if (updateRequest.getRoomStatus() != null) {
            room.setRoomStatus(updateRequest.getRoomStatus());
        }
        if (updateRequest.getRoomName() != null) {
            room.setRoomName(updateRequest.getRoomName());
        }
        room.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        // Save the updated room
        roomRepository.save(room);

        // Return updated room details
        return mapRoomToRoomResponse(room);
    }

    public List<BookingResponse> getRoomBookings(Long roomId) {
        // link to the hotel-booking module
        // find the booking with the same roomId
        List<BookingResponse> roomBookings = webClientBuilder.build().get()
                .uri("http://localhost:8080/api/bookings",
                        uriBuilder -> uriBuilder.queryParam("roomId", roomId).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BookingResponse>>() {})
                .block();
        return roomBookings;
    }
}

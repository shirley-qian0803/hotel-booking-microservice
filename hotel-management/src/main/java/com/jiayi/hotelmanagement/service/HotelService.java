package com.jiayi.hotelmanagement.service;

import com.jiayi.hotelmanagement.dto.HotelRequest;
import com.jiayi.hotelmanagement.dto.HotelResponse;
import com.jiayi.hotelmanagement.dto.RoomResponse;
import com.jiayi.hotelmanagement.model.Hotel;
import com.jiayi.hotelmanagement.model.Room;
import com.jiayi.hotelmanagement.repository.HotelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.jiayi.hotelmanagement.service.RoomService;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class HotelService {

    private HotelRepository hotelRepository;
    public HotelResponse createHotel(HotelRequest hotelRequest) {
        // check the hotel is not created(no similar email)
        if (hotelRepository.findByHotelEmail(hotelRequest.getHotelEmail()).isPresent()){
            throw new IllegalArgumentException("The hotel is already created");
        }
        Hotel hotel = Hotel
                .builder()
                .name(hotelRequest.getName())
                .managerId(hotelRequest.getManagerId())
                .location(hotelRequest.getLocation())
                .hotelEmail(hotelRequest.getHotelEmail())
                .rooms(hotelRequest.getRooms())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
        hotelRepository.save(hotel);
        log.info("The hotel {} is created", hotel.getHotelId());
        return makeHotelResponse(hotel);
    }

    public HotelResponse getHotel(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
        return makeHotelResponse(hotel);
    }

    private HotelResponse makeHotelResponse(Hotel hotel){
        return HotelResponse
                .builder()
                .name(hotel.getName())
                .managerId(hotel.getManagerId())  // Assuming there's a Manager entity with getId()
                .hotelEmail(hotel.getHotelEmail())
                .location(hotel.getLocation())
                .createdAt(hotel.getCreatedAt())
                .updatedAt(hotel.getUpdatedAt())
                .roomResponses(hotel.getRooms().stream().map(this::mapRoomToRoomResponse).toList())
                .build();
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

    public List<HotelResponse> searchNearBy(double lat, double lon, double radius) {
        String point = "POINT(" + lon + " " + lat + ")";
        return hotelRepository.findHotelsNearLocation(point, radius)
                .stream()
                .map(this::makeHotelResponse)
                .collect(Collectors.toList());
    }

    public List<HotelResponse> searchByName(String name) {
        return hotelRepository.findByNameLike(name)
                .stream()
                .map(this::makeHotelResponse)
                .collect(Collectors.toList());
    }
}

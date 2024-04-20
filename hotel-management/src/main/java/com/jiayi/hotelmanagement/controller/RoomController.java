package com.jiayi.hotelmanagement.controller;

import com.jiayi.hotelmanagement.dto.BookingResponse;
import com.jiayi.hotelmanagement.dto.RoomRequest;
import com.jiayi.hotelmanagement.dto.RoomResponse;
import com.jiayi.hotelmanagement.dto.UpdateRequest;
import com.jiayi.hotelmanagement.model.Room;
import com.jiayi.hotelmanagement.repository.RoomRepository;
import com.jiayi.hotelmanagement.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@AllArgsConstructor
public class RoomController {
    private RoomRepository roomRepository;
    private RoomService roomService;

    @PostMapping("/{hotelId}/room/create")
    public ResponseEntity<RoomResponse> createRoom(@PathVariable Long hotelId, @RequestBody RoomRequest roomRequest) {
        RoomResponse roomResponse = roomService.createRoom(hotelId, roomRequest);
        return new ResponseEntity<>(roomResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{hotelId}/room/{roomId}/update")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody UpdateRequest updateRequest) {
//        Room room = roomRepository.findById(roomId).orElseThrow();
        RoomResponse roomResponse = roomService.updateRoom(hotelId, roomId, updateRequest);
        return new ResponseEntity<>(roomResponse, HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/room/{roomId}/bookings")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingResponse> getRoomBookings(@PathVariable Long roomId) {
        return roomService.getRoomBookings(roomId);
    }
}

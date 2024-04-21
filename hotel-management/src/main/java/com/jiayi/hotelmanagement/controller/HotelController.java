package com.jiayi.hotelmanagement.controller;


import com.jiayi.hotelmanagement.dto.HotelRequest;
import com.jiayi.hotelmanagement.dto.HotelResponse;
import com.jiayi.hotelmanagement.model.Address;
import com.jiayi.hotelmanagement.model.Hotel;
import com.jiayi.hotelmanagement.repository.HotelRepository;
import com.jiayi.hotelmanagement.service.HotelService;
import lombok.AllArgsConstructor;
import org.elasticsearch.geometry.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@AllArgsConstructor
public class HotelController {
    private HotelService hotelService;

    @PostMapping("/create")
    public HotelResponse createHotel(@RequestBody HotelRequest hotelRequest) {
        return hotelService.createHotel(hotelRequest);
    }

    @GetMapping("/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public HotelResponse getHotel(@PathVariable Long hotelId) {
        return hotelService.getHotel(hotelId);
    }


    @GetMapping("/list")
    public List<HotelResponse> listHotels() {
        return hotelService.listHotels();
    }

    @GetMapping("/search/nearby")
    public List<HotelResponse> searchNearBy(@RequestParam double lat, @RequestParam double lon, @RequestParam double radius) {
        return hotelService.searchNearBy(lat, lon, radius);
    }

    @GetMapping("/search")
    public List<HotelResponse> searchByName(@RequestParam String name) {
        return hotelService.searchByName(name);
    }

}

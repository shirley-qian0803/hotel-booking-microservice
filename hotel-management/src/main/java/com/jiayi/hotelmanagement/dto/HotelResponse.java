package com.jiayi.hotelmanagement.dto;

import com.jiayi.hotelmanagement.model.Address;
import com.jiayi.hotelmanagement.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.locationtech.jts.geom.Point;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {
    private String name;
    private Long managerId;
    private String hotelEmail;
    private Point location;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<RoomResponse> roomResponses;
}

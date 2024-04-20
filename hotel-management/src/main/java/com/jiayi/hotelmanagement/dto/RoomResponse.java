package com.jiayi.hotelmanagement.dto;

import com.jiayi.hotelmanagement.model.RoomStatus;
import com.jiayi.hotelmanagement.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {
    private Long roomId;
    private Double roomPrice;
    private String roomName;
    private RoomType roomType;
    private RoomStatus roomStatus;
    // only return the hotelId
    private Long hotelId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

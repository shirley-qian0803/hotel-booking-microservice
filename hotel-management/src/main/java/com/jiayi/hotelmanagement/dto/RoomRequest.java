package com.jiayi.hotelmanagement.dto;

import com.jiayi.hotelmanagement.model.RoomStatus;
import com.jiayi.hotelmanagement.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private Double roomPrice;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private String roomName;
}

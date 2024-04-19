package com.jiayi.hotelbooking.dto;

import com.jiayi.hotelbooking.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long userId;
    private Long hotelId;
    private Long roomId;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private Status status;
}

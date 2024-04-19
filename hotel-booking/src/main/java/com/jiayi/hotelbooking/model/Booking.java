package com.jiayi.hotelbooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "t_Booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;
    private Long userId;
    private Long hotelId;
    private Long roomId;

    @Enumerated(EnumType.STRING)
    private Status status;


    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
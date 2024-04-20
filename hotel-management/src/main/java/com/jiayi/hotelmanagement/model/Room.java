package com.jiayi.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "t_Room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    private Double roomPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private String roomName; // Every hotel's room has different names

    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hotel hotel;
}

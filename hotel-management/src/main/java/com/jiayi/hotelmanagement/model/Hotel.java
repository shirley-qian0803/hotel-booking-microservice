package com.jiayi.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "t_Hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String name;
    private Long managerId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Point location; // Use Point type from JTS library

    @Column(unique = true)
    private String hotelEmail;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;
}

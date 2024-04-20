package com.jiayi.hotelmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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

    @Embedded
    private Address address;
    @Column(unique = true)
    private String hotelEmail;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;
}

package com.jiayi.hotelmanagement.model;

import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "t_Hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "hotel")
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

    @Field(type = FieldType.GeoPoint)
    private GeoPoint location;  // GeoPoint uses latitude and longitude

    @Column(unique = true)
    private String hotelEmail;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;
}

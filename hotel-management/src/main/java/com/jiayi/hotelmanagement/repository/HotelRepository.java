package com.jiayi.hotelmanagement.repository;

import com.jiayi.hotelmanagement.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByHotelEmail(String hotelEmail);

    @Query("SELECT h FROM Hotel h WHERE ST_Distance_Sphere(h.location, ST_GeomFromText(:point, 4326)) <= :distance")
    List<Hotel> findHotelsNearLocation(@Param("point") String point, @Param("distance") double distance);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Hotel> findByNameLike(@Param("name") String name);
}

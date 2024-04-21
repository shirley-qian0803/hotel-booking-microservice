package com.jiayi.hotelmanagement.repository;

import com.jiayi.hotelmanagement.model.Hotel;
import org.elasticsearch.geometry.Point;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.geo.Distance;

import java.util.List;

public interface HotelElasticRepository extends ElasticsearchRepository<Hotel, String> {
    List<Hotel> findByNameContaining(String name);

    List<Hotel> findByLocationNear(GeoPoint location, Distance distance);

}

package com.foodlink.domain.repository;

import com.foodlink.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findBySlug(String slug);

    Optional<Restaurant> findByGooglePlaceId(String googlePlaceId);

    Optional<Restaurant> findByNameAndAreaAndCity(String name, String area, String city);
}

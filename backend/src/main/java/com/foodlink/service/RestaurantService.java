package com.foodlink.service;

import com.foodlink.domain.entity.Restaurant;
import com.foodlink.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant findOrCreateRestaurant(String name, String area, String googleMapsUrl) {
        // Try to find existing restaurant
        return restaurantRepository.findByNameAndAreaAndCity(name, area, "Delhi")
                .orElseGet(() -> {
                    // Create new restaurant
                    Restaurant restaurant = Restaurant.builder()
                            .name(name)
                            .slug(generateSlug(name))
                            .area(area)
                            .city("Delhi")
                            .googleMapsUrl(googleMapsUrl)
                            .build();
                    
                    return restaurantRepository.save(restaurant);
                });
    }

    private String generateSlug(String name) {
        String baseSlug = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
        
        String slug = baseSlug;
        int counter = 1;
        
        while (restaurantRepository.findBySlug(slug).isPresent()) {
            slug = baseSlug + "-" + counter++;
        }
        
        return slug;
    }
}

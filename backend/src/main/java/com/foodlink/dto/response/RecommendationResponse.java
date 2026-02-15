package com.foodlink.dto.response;

import com.foodlink.domain.entity.Recommendation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private Long id;
    private String dishName;
    private String restaurantName;
    private String area;
    private String city;
    private String fullAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String googleMapsUrl;
    private String creatorQuote;
    private String description;
    private DishCategory dishCategory;
    private CuisineType cuisineType;
    private MealType mealType;
    private PriceRange priceRange;
    private String[] tags;
    private Integer viewCount;
    private Integer instagramClicks;
    private Integer mapsClicks;
    private Integer saveCount;
    private String thumbnailUrl;
    private String instagramUrl;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
}

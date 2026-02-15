package com.foodlink.dto.request;

import com.foodlink.domain.entity.Recommendation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class CreateRecommendationRequest {

    private Long videoId;

    @NotBlank(message = "Dish name is required")
    @Size(max = 255, message = "Dish name cannot exceed 255 characters")
    private String dishName;

    @NotBlank(message = "Restaurant name is required")
    @Size(max = 255, message = "Restaurant name cannot exceed 255 characters")
    private String restaurantName;

    private String area;

    private String fullAddress;

    private String googleMapsUrl;

    private String creatorQuote;

    private String description;

    private DishCategory dishCategory;

    private CuisineType cuisineType;

    private MealType mealType;

    private PriceRange priceRange;

    private String[] tags;
}

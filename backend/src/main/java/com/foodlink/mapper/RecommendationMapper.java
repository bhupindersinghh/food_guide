package com.foodlink.mapper;

import com.foodlink.domain.entity.Recommendation;
import com.foodlink.dto.response.RecommendationResponse;
import org.springframework.stereotype.Component;

@Component
public class RecommendationMapper {

    public RecommendationResponse toResponse(Recommendation recommendation) {
        return RecommendationResponse.builder()
                .id(recommendation.getId())
                .dishName(recommendation.getDishName())
                .restaurantName(recommendation.getRestaurantName())
                .area(recommendation.getArea())
                .city(recommendation.getCity())
                .fullAddress(recommendation.getFullAddress())
                .latitude(recommendation.getLatitude())
                .longitude(recommendation.getLongitude())
                .googleMapsUrl(recommendation.getGoogleMapsUrl())
                .creatorQuote(recommendation.getCreatorQuote())
                .description(recommendation.getDescription())
                .dishCategory(recommendation.getDishCategory())
                .cuisineType(recommendation.getCuisineType())
                .mealType(recommendation.getMealType())
                .priceRange(recommendation.getPriceRange())
                .tags(recommendation.getTags())
                .viewCount(recommendation.getViewCount())
                .instagramClicks(recommendation.getInstagramClicks())
                .mapsClicks(recommendation.getMapsClicks())
                .saveCount(recommendation.getSaveCount())
                .thumbnailUrl(recommendation.getVideo() != null ? 
                        recommendation.getVideo().getThumbnailUrl() : null)
                .instagramUrl(recommendation.getVideo() != null ? 
                        recommendation.getVideo().getInstagramUrl() : null)
                .publishedAt(recommendation.getPublishedAt())
                .createdAt(recommendation.getCreatedAt())
                .build();
    }
}

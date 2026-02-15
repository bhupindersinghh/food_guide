package com.foodlink.service;

import com.foodlink.domain.entity.Creator;
import com.foodlink.domain.entity.Recommendation;
import com.foodlink.domain.entity.Restaurant;
import com.foodlink.domain.entity.Video;
import com.foodlink.domain.repository.CreatorRepository;
import com.foodlink.domain.repository.RecommendationRepository;
import com.foodlink.domain.repository.RestaurantRepository;
import com.foodlink.domain.repository.VideoRepository;
import com.foodlink.dto.request.CreateRecommendationRequest;
import com.foodlink.dto.response.RecommendationResponse;
import com.foodlink.exception.ResourceNotFoundException;
import com.foodlink.mapper.RecommendationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final CreatorRepository creatorRepository;
    private final VideoRepository videoRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final RecommendationMapper recommendationMapper;

    public RecommendationService(RecommendationRepository recommendationRepository, CreatorRepository creatorRepository, VideoRepository videoRepository, RestaurantRepository restaurantRepository, RestaurantService restaurantService, RecommendationMapper recommendationMapper) {
        this.recommendationRepository = recommendationRepository;
        this.creatorRepository = creatorRepository;
        this.videoRepository = videoRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.recommendationMapper = recommendationMapper;
    }

    @Transactional
    public RecommendationResponse createRecommendation(CreateRecommendationRequest request, Long creatorId) {
        Creator creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found"));

        Video video = null;
        if (request.getVideoId() != null) {
            video = videoRepository.findByIdAndCreatorId(request.getVideoId(), creatorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Video not found or doesn't belong to creator"));
        }

        // Try to find or create restaurant
        Restaurant restaurant = null;
        if (request.getGoogleMapsUrl() != null && !request.getGoogleMapsUrl().isBlank()) {
            restaurant = restaurantService.findOrCreateRestaurant(
                    request.getRestaurantName(),
                    request.getArea(),
                    request.getGoogleMapsUrl()
            );
        }

        Recommendation recommendation = Recommendation.builder()
                .creator(creator)
                .video(video)
                .restaurant(restaurant)
                .dishName(request.getDishName())
                .restaurantName(request.getRestaurantName())
                .area(request.getArea())
                .fullAddress(request.getFullAddress())
                .googleMapsUrl(request.getGoogleMapsUrl())
                .creatorQuote(request.getCreatorQuote())
                .description(request.getDescription())
                .dishCategory(request.getDishCategory())
                .cuisineType(request.getCuisineType())
                .mealType(request.getMealType())
                .priceRange(request.getPriceRange())
                .tags(request.getTags())
                .status(Recommendation.RecommendationStatus.PUBLISHED)
                .publishedAt(LocalDateTime.now())
                .build();

        recommendation = recommendationRepository.save(recommendation);

        // Update creator stats (handle null values)
        Integer currentCount = creator.getTotalRecommendations();
        creator.setTotalRecommendations((currentCount != null ? currentCount : 0) + 1);
        creatorRepository.save(creator);

        return recommendationMapper.toResponse(recommendation);
    }

    @Transactional(readOnly = true)
    public List<RecommendationResponse> getCreatorRecommendations(Long creatorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"));
        Page<Recommendation> recommendations = recommendationRepository.findByCreatorIdAndStatus(
                creatorId,
                Recommendation.RecommendationStatus.PUBLISHED,
                pageable
        );

        return recommendations.stream()
                .map(recommendationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecommendationResponse> getPublicRecommendations(String creatorSlug, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"));
        Page<Recommendation> recommendations = recommendationRepository.findByCreatorSlugAndStatus(
                creatorSlug,
                Recommendation.RecommendationStatus.PUBLISHED,
                pageable
        );

        return recommendations.stream()
                .map(recommendationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void incrementViewCount(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
        
        Integer currentCount = recommendation.getViewCount();
        recommendation.setViewCount((currentCount != null ? currentCount : 0) + 1);
        recommendationRepository.save(recommendation);
    }

    @Transactional
    public void incrementInstagramClicks(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
        
        Integer currentCount = recommendation.getInstagramClicks();
        recommendation.setInstagramClicks((currentCount != null ? currentCount : 0) + 1);
        recommendationRepository.save(recommendation);
    }

    @Transactional
    public void incrementMapsClicks(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
        
        Integer currentCount = recommendation.getMapsClicks();
        recommendation.setMapsClicks((currentCount != null ? currentCount : 0) + 1);
        recommendationRepository.save(recommendation);
    }
}

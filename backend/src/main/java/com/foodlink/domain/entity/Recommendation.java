package com.foodlink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations", indexes = {
        @Index(name = "idx_recommendations_creator", columnList = "creator_id"),
        @Index(name = "idx_recommendations_status", columnList = "status"),
        @Index(name = "idx_recommendations_category", columnList = "dish_category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String dishName;

    @Column(nullable = false)
    private String restaurantName;

    @Column(length = 100)
    private String area;

    @Column(length = 100)
    private String city = "Delhi";

    @Column(columnDefinition = "TEXT")
    private String fullAddress;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(columnDefinition = "TEXT")
    private String googleMapsUrl;

    @Column(columnDefinition = "TEXT")
    private String creatorQuote;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private DishCategory dishCategory;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CuisineType cuisineType;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PriceRange priceRange;

    @Column(columnDefinition = "text[]")
    private String[] tags;

    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer instagramClicks = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer mapsClicks = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer saveCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private RecommendationStatus status = RecommendationStatus.PUBLISHED;

    private LocalDateTime publishedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum DishCategory {
        BIRYANI, MOMOS, STREET_FOOD, FINE_DINING,
        DESSERT, BREAKFAST, KEBABS, CHINESE, SOUTH_INDIAN, OTHER
    }

    public enum CuisineType {
        NORTH_INDIAN, SOUTH_INDIAN, CHINESE, MUGHLAI,
        STREET_FOOD, CONTINENTAL, FAST_FOOD, OTHER
    }

    public enum MealType {
        BREAKFAST, LUNCH, DINNER, SNACK, BEVERAGE
    }

    public enum PriceRange {
        BUDGET,      // < 200 per person
        MID,         // 200-500
        PREMIUM,     // 500-1000
        LUXURY       // > 1000
    }

    public enum RecommendationStatus {
        DRAFT, PUBLISHED, ARCHIVED
    }
}

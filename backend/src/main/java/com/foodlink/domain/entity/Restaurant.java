package com.foodlink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurants", indexes = {
        @Index(name = "idx_restaurants_name", columnList = "name"),
        @Index(name = "idx_restaurants_area", columnList = "area")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String slug;

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

    @Column(unique = true)
    private String googlePlaceId;

    @Column(columnDefinition = "TEXT")
    private String googleMapsUrl;

    @Column(columnDefinition = "TEXT")
    private String zomatoUrl;

    @Column(columnDefinition = "TEXT")
    private String swiggyUrl;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "text[]")
    private String[] cuisineTypes;

    @Column(length = 20)
    private String priceRange;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalRecommendations = 0;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}

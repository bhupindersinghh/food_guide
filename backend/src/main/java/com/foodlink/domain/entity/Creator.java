package com.foodlink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "creators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String profileImageUrl;

    @Column(unique = true, length = 100)
    private String instagramHandle;

    @Column(nullable = false)
    private Boolean instagramVerified = false;

    @Column(unique = true, nullable = false, length = 100)
    private String slug;

    @Column(length = 7)
    private String themeColor = "#FF6B6B";

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SubscriptionTier subscriptionTier = SubscriptionTier.FREE;

    private LocalDateTime subscriptionExpiresAt;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalRecommendations = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalPageViews = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CreatorStatus status = CreatorStatus.ACTIVE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Recommendation> recommendations = new ArrayList<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Video> videos = new ArrayList<>();

    public enum SubscriptionTier {
        FREE, PREMIUM
    }

    public enum CreatorStatus {
        ACTIVE, SUSPENDED, PENDING
    }
}

package com.foodlink.dto.response;

import com.foodlink.domain.entity.Creator.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorResponse {
    private Long id;
    private String username;
    private String email;
    private String displayName;
    private String bio;
    private String profileImageUrl;
    private String instagramHandle;
    private Boolean instagramVerified;
    private String slug;
    private String themeColor;
    private SubscriptionTier subscriptionTier;
    private LocalDateTime subscriptionExpiresAt;
    private Integer totalRecommendations;
    private Integer totalPageViews;
    private CreatorStatus status;
    private LocalDateTime createdAt;
}

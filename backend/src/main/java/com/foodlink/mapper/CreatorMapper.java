package com.foodlink.mapper;

import com.foodlink.domain.entity.Creator;
import com.foodlink.dto.response.CreatorPublicResponse;
import com.foodlink.dto.response.CreatorResponse;
import org.springframework.stereotype.Component;

@Component
public class CreatorMapper {

    public CreatorResponse toResponse(Creator creator) {
        return CreatorResponse.builder()
                .id(creator.getId())
                .username(creator.getUsername())
                .email(creator.getEmail())
                .displayName(creator.getDisplayName())
                .bio(creator.getBio())
                .profileImageUrl(creator.getProfileImageUrl())
                .instagramHandle(creator.getInstagramHandle())
                .instagramVerified(creator.getInstagramVerified())
                .slug(creator.getSlug())
                .themeColor(creator.getThemeColor())
                .subscriptionTier(creator.getSubscriptionTier())
                .subscriptionExpiresAt(creator.getSubscriptionExpiresAt())
                .totalRecommendations(creator.getTotalRecommendations())
                .totalPageViews(creator.getTotalPageViews())
                .status(creator.getStatus())
                .createdAt(creator.getCreatedAt())
                .build();
    }

    public CreatorPublicResponse toPublicResponse(Creator creator) {
        return CreatorPublicResponse.builder()
                .username(creator.getUsername())
                .displayName(creator.getDisplayName())
                .bio(creator.getBio())
                .profileImageUrl(creator.getProfileImageUrl())
                .instagramHandle(creator.getInstagramHandle())
                .slug(creator.getSlug())
                .themeColor(creator.getThemeColor())
                .totalRecommendations(creator.getTotalRecommendations())
                .totalPageViews(creator.getTotalPageViews())
                .build();
    }
}

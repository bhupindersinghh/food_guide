package com.foodlink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorPublicResponse {
    private String username;
    private String displayName;
    private String bio;
    private String profileImageUrl;
    private String instagramHandle;
    private String slug;
    private String themeColor;
    private Integer totalRecommendations;
    private Integer totalPageViews;
}

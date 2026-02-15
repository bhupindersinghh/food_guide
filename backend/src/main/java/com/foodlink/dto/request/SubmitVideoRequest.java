package com.foodlink.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubmitVideoRequest {

    @NotBlank(message = "Instagram URL is required")
    @Pattern(
        regexp = "^https?://(www\\.)?(instagram\\.com|instagr\\.am)/(p|reel|tv)/[A-Za-z0-9_-]+/?.*$",
        message = "Invalid Instagram URL"
    )
    private String instagramUrl;
}

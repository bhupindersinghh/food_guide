package com.foodlink.controller;

import com.foodlink.dto.request.TrackEventRequest;
import com.foodlink.dto.response.ApiResponse;
import com.foodlink.dto.response.CreatorPublicResponse;
import com.foodlink.dto.response.RecommendationResponse;
import com.foodlink.service.AnalyticsService;
import com.foodlink.service.CreatorService;
import com.foodlink.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
@Tag(name = "Public APIs", description = "Public APIs accessible without authentication")
public class PublicController {

    private final CreatorService creatorService;
    private final RecommendationService recommendationService;
    private final AnalyticsService analyticsService;

    @GetMapping("/creators/{slug}")
    @Operation(summary = "Get creator's public profile by slug")
    public ResponseEntity<ApiResponse<CreatorPublicResponse>> getCreator(@PathVariable String slug) {
        CreatorPublicResponse creator = creatorService.getCreatorBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(creator));
    }

    @GetMapping("/creators/{slug}/recommendations")
    @Operation(summary = "Get creator's recommendations")
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getRecommendations(
            @PathVariable String slug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<RecommendationResponse> recommendations = 
                recommendationService.getPublicRecommendations(slug, page, size);
        return ResponseEntity.ok(ApiResponse.success(recommendations));
    }

    @PostMapping("/events")
    @Operation(summary = "Track analytics event")
    public ResponseEntity<Void> trackEvent(
            @Valid @RequestBody TrackEventRequest request,
            HttpServletRequest httpRequest) {
        analyticsService.trackEvent(request, httpRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

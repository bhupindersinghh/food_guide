package com.foodlink.controller;

import com.foodlink.dto.request.CreateRecommendationRequest;
import com.foodlink.dto.response.ApiResponse;
import com.foodlink.dto.response.CreatorResponse;
import com.foodlink.dto.response.RecommendationResponse;
import com.foodlink.security.UserPrincipal;
import com.foodlink.service.CreatorService;
import com.foodlink.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/creator")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Creator APIs", description = "APIs for authenticated creators")
public class CreatorController {

    private final CreatorService creatorService;
    private final RecommendationService recommendationService;

    public CreatorController(CreatorService creatorService, RecommendationService recommendationService) {
        this.creatorService = creatorService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/profile")
    @Operation(summary = "Get current creator's profile")
    public ResponseEntity<ApiResponse<CreatorResponse>> getProfile(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        CreatorResponse creator = creatorService.getCreatorProfile(currentUser.getId());
        return ResponseEntity.ok(ApiResponse.success(creator));
    }

    @PostMapping("/recommendations")
    @Operation(summary = "Create a new recommendation")
    public ResponseEntity<ApiResponse<RecommendationResponse>> createRecommendation(
            @Valid @RequestBody CreateRecommendationRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        RecommendationResponse recommendation = 
                recommendationService.createRecommendation(request, currentUser.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recommendation created successfully", recommendation));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get creator's own recommendations")
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getMyRecommendations(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<RecommendationResponse> recommendations = 
                recommendationService.getCreatorRecommendations(currentUser.getId(), page, size);
        return ResponseEntity.ok(ApiResponse.success(recommendations));
    }
}

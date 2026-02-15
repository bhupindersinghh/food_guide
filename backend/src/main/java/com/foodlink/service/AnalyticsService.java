package com.foodlink.service;

import com.foodlink.domain.entity.AnalyticsEvent;
import com.foodlink.domain.entity.Creator;
import com.foodlink.domain.repository.AnalyticsEventRepository;
import com.foodlink.domain.repository.CreatorRepository;
import com.foodlink.dto.request.TrackEventRequest;
import com.foodlink.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsEventRepository analyticsEventRepository;
    private final CreatorRepository creatorRepository;

    @Transactional
    public void trackEvent(TrackEventRequest request, HttpServletRequest httpRequest) {
        // Get creator ID from slug if provided (don't fail if not found)
        Long creatorId = null;
        if (request.getCreatorSlug() != null && !request.getCreatorSlug().isBlank()) {
            try {
                Creator creator = creatorRepository.findBySlug(request.getCreatorSlug())
                        .orElse(null);
                if (creator != null) {
                    creatorId = creator.getId();
                }
            } catch (Exception e) {
                // Log but don't fail - analytics shouldn't break the app
                System.err.println("Error finding creator for analytics: " + e.getMessage());
            }
        }

        try {
            AnalyticsEvent event = AnalyticsEvent.builder()
                    .creatorId(creatorId)
                    .recommendationId(request.getRecommendationId())
                    .eventType(request.getEventType())
                    .searchQuery(request.getSearchQuery())
                    .userAgent(httpRequest.getHeader("User-Agent"))
                    .ipAddress(getClientIpAddress(httpRequest))
                    .referrer(httpRequest.getHeader("Referer"))
                    .sessionId(request.getSessionId())
                    .build();

            analyticsEventRepository.save(event);
        } catch (Exception e) {
            // Log but don't fail - analytics shouldn't break the app
            System.err.println("Error saving analytics event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty()) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

package com.foodlink.dto.request;

import com.foodlink.domain.entity.AnalyticsEvent.EventType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class TrackEventRequest {

    @NotNull(message = "Event type is required")
    private EventType eventType;

    private Long recommendationId;

    private String creatorSlug;

    private String searchQuery;

    private UUID sessionId;

    private Map<String, String> metadata;
}

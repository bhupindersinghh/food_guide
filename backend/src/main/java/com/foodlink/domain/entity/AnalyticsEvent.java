package com.foodlink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "analytics_events", indexes = {
        @Index(name = "idx_analytics_creator", columnList = "creator_id"),
        @Index(name = "idx_analytics_recommendation", columnList = "recommendation_id"),
        @Index(name = "idx_analytics_event_type", columnList = "event_type"),
        @Index(name = "idx_analytics_created_at", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnalyticsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "recommendation_id")
    private Long recommendationId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private EventType eventType;

    @Column(columnDefinition = "TEXT")
    private String searchQuery;

    @Column(columnDefinition = "TEXT")
    private String userAgent;

    @Column(columnDefinition = "TEXT")
    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String referrer;

    @Column(columnDefinition = "uuid")
    private UUID sessionId;

    private Long userId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum EventType {
        PAGE_VIEW, SEARCH, RECOMMENDATION_VIEW,
        INSTAGRAM_CLICK, MAPS_CLICK, SAVE
    }
}

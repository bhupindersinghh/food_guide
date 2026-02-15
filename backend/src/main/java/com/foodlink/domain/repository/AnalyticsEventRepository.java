package com.foodlink.domain.repository;

import com.foodlink.domain.entity.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, Long> {

    long countByCreatorIdAndEventTypeAndCreatedAtAfter(
            Long creatorId,
            AnalyticsEvent.EventType eventType,
            LocalDateTime after
    );

    @Query("SELECT e.searchQuery, COUNT(e) as count FROM AnalyticsEvent e " +
            "WHERE e.creatorId = :creatorId AND e.eventType = 'SEARCH' " +
            "AND e.createdAt >= :after " +
            "GROUP BY e.searchQuery ORDER BY count DESC")
    List<Object[]> findTopSearchQueriesByCreatorId(
            @Param("creatorId") Long creatorId,
            @Param("after") LocalDateTime after
    );

    @Query("SELECT DATE(e.createdAt) as date, COUNT(e) as count FROM AnalyticsEvent e " +
            "WHERE e.creatorId = :creatorId AND e.eventType = :eventType " +
            "AND e.createdAt >= :after " +
            "GROUP BY DATE(e.createdAt) ORDER BY date")
    List<Object[]> findDailyEventCounts(
            @Param("creatorId") Long creatorId,
            @Param("eventType") AnalyticsEvent.EventType eventType,
            @Param("after") LocalDateTime after
    );
}

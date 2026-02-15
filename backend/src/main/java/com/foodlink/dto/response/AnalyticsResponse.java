package com.foodlink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {
    private Long pageViews;
    private Long uniqueVisitors;
    private Long totalSearches;
    private List<TopRecommendation> topRecommendations;
    private List<String> topSearchQueries;
    private ChartData chartData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopRecommendation {
        private Long id;
        private String dishName;
        private String restaurantName;
        private Integer views;
        private Integer instagramClicks;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartData {
        private List<LocalDate> dates;
        private List<Long> pageViews;
    }
}

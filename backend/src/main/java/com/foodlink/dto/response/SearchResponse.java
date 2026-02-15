package com.foodlink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private List<RecommendationResponse> results;
    private PaginationInfo pagination;
    private Map<String, Map<String, Long>> aggregations;
}

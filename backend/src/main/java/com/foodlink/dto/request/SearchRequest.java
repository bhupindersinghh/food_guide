package com.foodlink.dto.request;

import com.foodlink.domain.entity.Recommendation.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchRequest {

    private String creatorSlug;
    private String query;
    private DishCategory[] categories;
    private String[] areas;
    private PriceRange[] priceRanges;
    private String[] tags;
    
    // Location-based search
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String radius = "5km";
    
    // Pagination
    private Integer page = 0;
    private Integer size = 20;
    
    // Sorting
    private String sortBy = "relevance"; // relevance, newest, popular, distance
}

package com.foodlink.service;

import com.foodlink.domain.entity.Creator;
import com.foodlink.domain.repository.CreatorRepository;
import com.foodlink.dto.response.CreatorPublicResponse;
import com.foodlink.dto.response.CreatorResponse;
import com.foodlink.exception.ResourceNotFoundException;
import com.foodlink.mapper.CreatorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final CreatorMapper creatorMapper;

    public CreatorService(CreatorRepository creatorRepository, CreatorMapper creatorMapper) {
        this.creatorRepository = creatorRepository;
        this.creatorMapper = creatorMapper;
    }

    @Transactional(readOnly = true)
    public CreatorPublicResponse getCreatorBySlug(String slug) {
        Creator creator = creatorRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with slug: " + slug));
        
        return creatorMapper.toPublicResponse(creator);
    }

    @Transactional(readOnly = true)
    public CreatorResponse getCreatorProfile(Long creatorId) {
        Creator creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found"));
        
        return creatorMapper.toResponse(creator);
    }

    @Transactional
    public void updateStats(Long creatorId, int recommendationsDelta, int pageViewsDelta) {
        Creator creator = creatorRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found"));
        
        // Handle null values
        Integer currentRecs = creator.getTotalRecommendations();
        Integer currentViews = creator.getTotalPageViews();
        
        creator.setTotalRecommendations((currentRecs != null ? currentRecs : 0) + recommendationsDelta);
        creator.setTotalPageViews((currentViews != null ? currentViews : 0) + pageViewsDelta);
        
        creatorRepository.save(creator);
    }
}

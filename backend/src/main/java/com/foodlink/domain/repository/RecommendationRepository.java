package com.foodlink.domain.repository;

import com.foodlink.domain.entity.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Page<Recommendation> findByCreatorIdAndStatus(
            Long creatorId,
            Recommendation.RecommendationStatus status,
            Pageable pageable
    );

    Page<Recommendation> findByCreatorSlugAndStatus(
            String creatorSlug,
            Recommendation.RecommendationStatus status,
            Pageable pageable
    );

    @Query("SELECT r FROM Recommendation r WHERE r.creator.slug = :slug AND r.status = 'PUBLISHED' " +
            "AND (LOWER(r.dishName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.restaurantName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.area) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Recommendation> searchByCreatorSlugAndQuery(
            @Param("slug") String slug,
            @Param("query") String query,
            Pageable pageable
    );

    List<Recommendation> findTop10ByCreatorIdAndStatusOrderByViewCountDesc(
            Long creatorId,
            Recommendation.RecommendationStatus status
    );

    Optional<Recommendation> findByIdAndCreatorId(Long id, Long creatorId);

    long countByCreatorIdAndStatus(Long creatorId, Recommendation.RecommendationStatus status);
}

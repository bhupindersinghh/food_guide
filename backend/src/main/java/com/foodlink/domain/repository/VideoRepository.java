package com.foodlink.domain.repository;

import com.foodlink.domain.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findByCreatorId(Long creatorId, Pageable pageable);

    List<Video> findByCreatorIdAndProcessingStatus(
            Long creatorId,
            Video.ProcessingStatus status
    );

    Optional<Video> findByIdAndCreatorId(Long id, Long creatorId);

    boolean existsByInstagramUrl(String instagramUrl);

    Optional<Video> findByInstagramPostId(String instagramPostId);
}

package com.foodlink.domain.repository;

import com.foodlink.domain.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {

    Optional<Creator> findByEmail(String email);

    Optional<Creator> findByUsername(String username);

    Optional<Creator> findBySlug(String slug);

    Optional<Creator> findByInstagramHandle(String instagramHandle);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsBySlug(String slug);

    boolean existsByInstagramHandle(String instagramHandle);
}

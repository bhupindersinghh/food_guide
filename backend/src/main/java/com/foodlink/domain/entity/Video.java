package com.foodlink.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "videos", indexes = {
        @Index(name = "idx_videos_creator", columnList = "creator_id"),
        @Index(name = "idx_videos_status", columnList = "processing_status"),
        @Index(name = "idx_videos_instagram_post", columnList = "instagram_post_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Creator creator;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instagramUrl;

    @Column(length = 50)
    private String instagramPostId;

    @Column(length = 500)
    private String thumbnailUrl;

    private Integer videoDurationSeconds;

    private LocalDateTime postedAt;

    @Column(length = 500)
    private String audioFileUrl;

    @Column(columnDefinition = "TEXT")
    private String transcript;

    @Column(length = 10)
    private String transcriptLanguage = "hi-IN";

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    @Builder.Default
    private ProcessingStatus processingStatus = ProcessingStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String processingError;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public enum ProcessingStatus {
        PENDING, DOWNLOADING, TRANSCRIBING, TRANSCRIBED, FAILED, COMPLETED
    }
}

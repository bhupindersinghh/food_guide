package com.foodlink.dto.response;

import com.foodlink.domain.entity.Video.ProcessingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {
    private Long id;
    private String instagramUrl;
    private String instagramPostId;
    private String thumbnailUrl;
    private Integer videoDurationSeconds;
    private String transcript;
    private ProcessingStatus processingStatus;
    private String processingError;
    private Integer progress;
    private String message;
    private LocalDateTime createdAt;
}

package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class StoryDetailedResponseDto {

    private Long id;
    private String storyUrl;
    private String description;
    private LocalDateTime storyCreatedAt;
    private boolean isActive;
    private int viewCount;
    private List<StoryViewResponseDto> storyViews;
}
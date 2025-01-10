package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class StoryResponseDto {

    private Long id ;
    private String storyUrl;
    private String description;
    private LocalDateTime storyCreatedAt;
    private boolean isActive;
    private int viewCount;
}
package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class StoryViewResponseDto {

    private Long id;
    private LocalDateTime viewedAt;
    private Long storyId;
    private Long userId;
}
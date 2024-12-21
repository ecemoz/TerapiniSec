package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StoryviewResponseDto {

    private Long id;
    private LocalDateTime viewedAt;
    private Long storyId;
    private Long userId;
}
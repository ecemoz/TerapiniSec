package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoryViewCreateDto {

    private Long storyId;
    private Long userId;
}
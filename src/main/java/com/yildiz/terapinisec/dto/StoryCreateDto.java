package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoryCreateDto {

    private String storyUrl;
    private String description;
    private boolean isActive;
}

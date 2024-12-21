package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class StoryCreateDto {

    private String storyUrl;
    private String description;
    private boolean isActive;
}

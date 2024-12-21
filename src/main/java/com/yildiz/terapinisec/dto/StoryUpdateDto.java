package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class StoryUpdateDto {

    private String storyUrl;
    private String description;
    private boolean isActive;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.MeditationSessionType;
import lombok.Data;

@Data
public class MeditationContentCreateDto {

    private String title;
    private String description;
    private MeditationSessionType meditationSessionType;
    private String contentUrl;
    private boolean isPublic;
    private Long createdById;
}
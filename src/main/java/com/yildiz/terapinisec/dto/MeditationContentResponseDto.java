package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.MeditationSessionType;
import lombok.Data;

@Data
public class MeditationContentResponseDto {

    private Long id;
    private String title;
    private String description;
    private MeditationSessionType meditationSessionType;
    private String contentUrl;
    private boolean isPublic;
    private String createdByUsername;
}

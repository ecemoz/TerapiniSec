package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.util.MeditationSessionType;
import lombok.Data;

@Data
public class MeditationContentUpdateDto {

    private String title;
    private String description;
    private MeditationSessionType meditationSessionType;
    private String contentUrl;
    private boolean isPublic;
}

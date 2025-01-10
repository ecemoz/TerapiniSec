package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.MeditationSessionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeditationContentUpdateDto {

        private String title;
        private String description;
        private MeditationSessionType meditationSessionType;
        private String contentUrl;
        private boolean isPublic;
}
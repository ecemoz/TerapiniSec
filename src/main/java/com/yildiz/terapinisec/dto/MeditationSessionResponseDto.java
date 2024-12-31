package com.yildiz.terapinisec.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class MeditationSessionResponseDto {

    private Long id ;
    private Duration meditationSessionDuration;
    private LocalDateTime meditationDateTime;
    private String meditatorUsername;
    private String meditationContentTitle;
}

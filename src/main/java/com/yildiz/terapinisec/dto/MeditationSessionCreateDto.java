package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class MeditationSessionCreateDto {

    private Duration meditationSessionDuration;
    private LocalDateTime meditationDateTime;
    private Long meditatorId;
    private Long meditationContentId;
}

package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
public class MeditationSessionCreateDto {

    private Duration meditationSessionDuration;
    private LocalDateTime meditationDateTime;
    private Long meditatorId;
    private Long meditationContentId;
}

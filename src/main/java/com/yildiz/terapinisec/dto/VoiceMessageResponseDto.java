package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class VoiceMessageResponseDto {

    private Long id;
    private String audioUrl;
    private LocalDateTime timestamp;
    private Long speakerId;
    private Long listenerId;
}
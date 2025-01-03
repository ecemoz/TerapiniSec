package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VoiceMessageResponseDto {

    private Long id;
    private String audioUrl;
    private LocalDateTime timestamp;
    private Long speakerId;
    private Long listenerId;
}
package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoiceMessageCreateDto {

    private String audioUrl;
    private Long speakerId;
    private Long listenerId;
}
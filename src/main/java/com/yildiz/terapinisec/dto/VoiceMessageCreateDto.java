package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class VoiceMessageCreateDto {

    private String audioUrl;
    private Long speakerId;
    private Long listenerId;
}
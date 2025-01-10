package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.VoiceMessageCreateDto;
import com.yildiz.terapinisec.dto.VoiceMessageResponseDto;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.model.VoiceMessage;
import org.springframework.stereotype.Component;

@Component
public class VoiceMessageMapper {

    public VoiceMessage toVoiceMessage(VoiceMessageCreateDto createDto, User speaker, User listener) {
        if (createDto == null || speaker == null || listener == null) {
            return null;
        }
        return VoiceMessage.builder()
                .audioUrl(createDto.getAudioUrl())
                .speaker(speaker)
                .listener(listener)
                .build();
    }

    public VoiceMessageResponseDto toVoiceMessageResponseDto(VoiceMessage voiceMessage) {
        if (voiceMessage == null) {
            return null;
        }
        return VoiceMessageResponseDto.builder()
                .id(voiceMessage.getId())
                .audioUrl(voiceMessage.getAudioUrl())
                .timestamp(voiceMessage.getTimeStamp())
                .speakerId(voiceMessage.getSpeaker() != null ? voiceMessage.getSpeaker().getId() : null)
                .listenerId(voiceMessage.getListener() != null ? voiceMessage.getListener().getId() : null)
                .build();
    }
}
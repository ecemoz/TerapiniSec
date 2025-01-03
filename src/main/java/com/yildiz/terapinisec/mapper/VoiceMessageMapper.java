package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.VoiceMessageCreateDto;
import com.yildiz.terapinisec.dto.VoiceMessageResponseDto;
import com.yildiz.terapinisec.model.VoiceMessage;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoiceMessageMapper {

    @Mapping(target = "speaker" , ignore = true)
    VoiceMessage toEntity(VoiceMessageCreateDto voiceMessageCreateDto);

    @Mapping(source = "speaker.id" , target = "speakerId")
    @Mapping(source = "listener.id" , target = "listenerId")
    VoiceMessageResponseDto toResponseDto(VoiceMessage voiceMessage);

    @IterableMapping(elementTargetType = VoiceMessageResponseDto.class)
    List<VoiceMessageResponseDto> toVoiceMessageResponseDtoList(List<VoiceMessage> voiceMessages);
}
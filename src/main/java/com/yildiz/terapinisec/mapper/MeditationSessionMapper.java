package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.MeditationSessionCreateDto;
import com.yildiz.terapinisec.dto.MeditationSessionResponseDto;
import com.yildiz.terapinisec.model.MeditationSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeditationSessionMapper {

    @Mapping(source = "meditatorId" , target = "meditator.id")
    @Mapping(source = "meditationContentId" , target = "meditationContent.id" )
    MeditationSession toMeditationSession(MeditationSessionCreateDto meditationSessionCreateDto);

    @Mapping(source = "meditator.username" , target = "meditatorUsername")
    @Mapping(source = "meditationContent.title" , target = "meditationContentTitle")
    MeditationSessionResponseDto toMeditationSessionResponseDto(MeditationSession meditationSession);
}
package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.MeditationContentCreateDto;
import com.yildiz.terapinisec.dto.MeditationContentResponseDto;
import com.yildiz.terapinisec.dto.MeditationContentUpdateDto;
import com.yildiz.terapinisec.model.MeditationContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MeditationContentMapper {

    @Mapping(source = "createdById" , target = "createdBy.id")
    MeditationContent toMeditationContent(MeditationContentCreateDto meditationContentCreateDto);

    @Mapping(source = "createdBy.username" , target = "createdByUsername")
    MeditationContentResponseDto toMeditationContentResponseDto(MeditationContent meditationContent);
    
    void updateMeditationContentFromDto(MeditationContentUpdateDto meditationContentUpdateDto, @MappingTarget MeditationContent meditationContent);
}


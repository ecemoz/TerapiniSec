package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SurveyCreateDto;
import com.yildiz.terapinisec.dto.SurveyPostDto;
import com.yildiz.terapinisec.dto.SurveyUpdateDto;
import com.yildiz.terapinisec.model.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SurveyMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "surveyCreatedAt", ignore = true)
    @Mapping(target = "surveyResponses" , ignore = true)
    @Mapping(source = "createdById" , target = "createdBy.id")
    Survey toSurvey (SurveyCreateDto surveyCreateDto);

    @Mapping(source = "createdBy.id" , target = "createdById")
    SurveyCreateDto toSurveyCreateDto(Survey survey);

    @Mapping(source = "createdBy.id", target = "createdById")
    SurveyPostDto toSurveyPostDto(Survey survey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "surveyCreatedAt", ignore = true)
    @Mapping(target = "surveyResponses" , ignore = true)
    @Mapping( target = "createdBy" , ignore = true)
    void updateSurveyFromDto(SurveyUpdateDto surveyUpdateDto, @MappingTarget Survey survey);
}
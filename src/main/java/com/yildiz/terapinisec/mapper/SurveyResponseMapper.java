package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponseDto;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.SurveyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SurveyResponseMapper {

    SurveyResponseMapper INSTANCE = Mappers.getMapper(SurveyResponseMapper.class);

    @Mapping(source = "userId" , target = "responsedBy.id")
    @Mapping(source = "surveyId" , target = "survey.id" )
    SurveyResponse toSurveyResponse(SurveyResponseCreateDto surveyResponseCreateDto);

    @Mapping( source = "responsedBy.username" , target = "respondedByUsername")
    SurveyResponseDto toSurveyResponseDto(SurveyResponse surveyResponse);
}
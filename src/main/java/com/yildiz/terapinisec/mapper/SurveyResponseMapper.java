package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.model.SurveyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
;

@Mapper(componentModel = "spring")
public interface SurveyResponseMapper {

    @Mapping(source = "userId" , target = "responsedBy.id")
    @Mapping(source = "surveyId" , target = "survey.id" )
    SurveyResponse toSurveyResponse(SurveyResponseCreateDto surveyResponseCreateDto);

    @Mapping( source = "responsedBy.username" , target = "respondedByUsername")
    SurveyResponsePostDto toSurveyResponseResponseDto(SurveyResponse surveyResponse);
}
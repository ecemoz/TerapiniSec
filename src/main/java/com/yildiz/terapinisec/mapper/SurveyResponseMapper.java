package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;

@Component
public class SurveyResponseMapper {

    public SurveyResponse toSurveyResponse(SurveyResponseCreateDto createDto, User user, Survey survey) {
        if (createDto == null || user == null || survey == null) {
            return null;
        }

        return SurveyResponse.builder()
                .responses(createDto.getResponses())
                .responsedBy(user)
                .survey(survey)
                .build();
    }

    public SurveyResponsePostDto toSurveyResponsePostDto(SurveyResponse surveyResponse) {
        if (surveyResponse == null) {
            return null;
        }

        return SurveyResponsePostDto.builder()
                .id(surveyResponse.getId())
                .responses(surveyResponse.getResponses())
                .submittedDate(surveyResponse.getSubmittedDate())
                .respondedByUsername(
                        surveyResponse.getResponsedBy() != null
                                ? surveyResponse.getResponsedBy().getUsername()
                                : null)
                .surveyId(
                        surveyResponse.getSurvey() != null
                                ? surveyResponse.getSurvey().getId()
                                : null)
                .build();
    }
}

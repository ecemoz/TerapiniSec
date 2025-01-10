package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.SurveyCreateDto;
import com.yildiz.terapinisec.dto.SurveyPostDto;
import com.yildiz.terapinisec.dto.SurveyUpdateDto;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;

@Component
public class SurveyMapper {

    public Survey toSurvey(SurveyCreateDto createDto, User createdBy) {
        if (createDto == null || createdBy == null) {
            return null;
        }

        return Survey.builder()
                .title(createDto.getTitle())
                .description(createDto.getDescription())
                .createdBy(createdBy)
                .build();
    }

    public SurveyPostDto toSurveyPostDto(Survey survey) {
        if (survey == null) {
            return null;
        }

        return SurveyPostDto.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .description(survey.getDescription())
                .surveyCreatedAt(survey.getSurveyCreatedAt())
                .createdById(survey.getCreatedBy() != null ? survey.getCreatedBy().getId() : null)
                .build();
    }

    public void updateSurveyFromDto(SurveyUpdateDto updateDto, Survey survey) {
        if (updateDto == null || survey == null) {
            return;
        }

        if (updateDto.getTitle() != null) {
            survey.setTitle(updateDto.getTitle());
        }

        if (updateDto.getDescription() != null) {
            survey.setDescription(updateDto.getDescription());
        }
    }
}

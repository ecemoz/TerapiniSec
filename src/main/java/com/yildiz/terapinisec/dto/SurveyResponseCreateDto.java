package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyResponseCreateDto {

    private String responses;
    private Long userId;
    private String surveyId;
}
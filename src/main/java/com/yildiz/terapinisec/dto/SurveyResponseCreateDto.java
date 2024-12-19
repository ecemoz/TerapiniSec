package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class SurveyResponseCreateDto {

    private String responses;
    private Long userId;
    private String surveyId;
}
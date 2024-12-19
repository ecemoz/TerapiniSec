package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class SurveyCreateDto {

    private String title;
    private String description;
    private Long createdById;
}
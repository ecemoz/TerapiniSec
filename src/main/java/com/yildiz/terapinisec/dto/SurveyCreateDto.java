package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyCreateDto {

    private String title;
    private String description;
    private Long createdById;
}
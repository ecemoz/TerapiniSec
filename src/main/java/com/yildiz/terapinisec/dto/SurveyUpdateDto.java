package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyUpdateDto {

    private String title;
    private String description;
}
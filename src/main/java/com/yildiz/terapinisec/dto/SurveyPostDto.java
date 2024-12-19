package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SurveyPostDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime surveyCreatedAt;
    private Long createdById;
}
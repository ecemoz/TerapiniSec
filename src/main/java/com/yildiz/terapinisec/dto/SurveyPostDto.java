package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class SurveyPostDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime surveyCreatedAt;
    private Long createdById;
}
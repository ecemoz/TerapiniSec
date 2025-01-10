package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class SurveyResponsePostDto {

    private Long id;
    private String responses;
    private LocalDateTime submittedDate;
    private String respondedByUsername;
    private Long surveyId;
}
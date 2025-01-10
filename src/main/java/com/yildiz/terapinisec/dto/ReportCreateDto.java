package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.ReportSituation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportCreateDto {

    private ReportSituation reportSituation;
    private String content;
    private Long userId;
}
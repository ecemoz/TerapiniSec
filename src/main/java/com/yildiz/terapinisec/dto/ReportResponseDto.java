package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.ReportSituation;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportResponseDto {

    private Long id;
    private ReportSituation reportSituation;
    private String content;
    private LocalDateTime reportCreatedAt;
    private String reportOwnerUsername;
}
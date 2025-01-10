package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.ReportCreateDto;
import com.yildiz.terapinisec.dto.ReportDetailedDto;
import com.yildiz.terapinisec.dto.ReportResponseDto;
import com.yildiz.terapinisec.model.Report;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    public Report toReport(ReportCreateDto createDto, User reportOwner) {
        if (createDto == null || reportOwner == null) {
            return null;
        }

        return Report.builder()
                .reportSituation(createDto.getReportSituation())
                .content(createDto.getContent())
                .reportOwner(reportOwner)
                .build();
    }

    public ReportResponseDto toReportResponseDto(Report report) {
        if (report == null) {
            return null;
        }

        return ReportResponseDto.builder()
                .id(report.getId())
                .reportSituation(report.getReportSituation())
                .content(report.getContent())
                .reportCreatedAt(report.getReportCreatedAt())
                .reportOwnerUsername(report.getReportOwner() != null ? report.getReportOwner().getUsername() : null)
                .build();
    }

    public ReportDetailedDto toReportDetailedDto(Report report) {
        if (report == null) {
            return null;
        }

        return ReportDetailedDto.builder()
                .id(report.getId())
                .reportSituation(report.getReportSituation())
                .content(report.getContent())
                .reportCreatedAt(report.getReportCreatedAt())
                .reportOwnerUsername(report.getReportOwner() != null ? report.getReportOwner().getUsername() : null)
                .reportOwnerEmail(report.getReportOwner() != null ? report.getReportOwner().getEmail() : null)
                .build();
    }

    public List<ReportResponseDto> toReportResponseDtoList(List<Report> reports) {
        if (reports == null || reports.isEmpty()) {
            return List.of();
        }

        return reports.stream()
                .map(this::toReportResponseDto)
                .collect(Collectors.toList());
    }

    public List<ReportDetailedDto> toReportDetailedDtoList(List<Report> reports) {
        if (reports == null || reports.isEmpty()) {
            return List.of();
        }

        return reports.stream()
                .map(this::toReportDetailedDto)
                .collect(Collectors.toList());
    }
}

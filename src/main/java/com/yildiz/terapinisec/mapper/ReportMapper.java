package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.ReportCreateDto;
import com.yildiz.terapinisec.dto.ReportDetailedDto;
import com.yildiz.terapinisec.dto.ReportResponseDto;
import com.yildiz.terapinisec.model.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Mapping(source = "userId" , target = "reportOwner.id")
    Report toReport(ReportCreateDto reportCreateDto);

    @Mapping( source = "reportOwner.username" , target = "reportOwnerUsername")
    ReportResponseDto toReportResponseDto(Report report);

    @Mapping(source = "reportOwner.username" , target = "reportOwnerUsername")
    @Mapping(source = "reportOwner.email" , target = "reportOwnerEmail")
    ReportDetailedDto toReportDetailedDto(Report report);

    List<ReportResponseDto> toReportResponseDtoList(List<Report> reports);

    List<ReportDetailedDto> toReportDetailedDtoList(List<Report> reports);
}

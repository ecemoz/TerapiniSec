package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository  extends JpaRepository<Report, Long> {

    Report findByReportType(String reportType);
    Report findByReportOwnerId(Long userId);
    Report findByReportOwnerIdAndReportType(Long userId, String reportType);
    List<Report> findByReportCreatedAt(LocalDateTime reportCreatedAt);
}

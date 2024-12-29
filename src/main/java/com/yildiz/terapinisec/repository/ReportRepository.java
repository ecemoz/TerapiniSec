package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository  extends JpaRepository<Report, Long> {

    Optional<Report> findByReportType(String reportType);
    Optional<Report> findByReportOwnerId(Long userId);
    Optional<Report> findByReportOwnerIdAndReportType(Long userId, String reportType);
    List<Report> findByReportCreatedAt(LocalDateTime reportCreatedAt);
}

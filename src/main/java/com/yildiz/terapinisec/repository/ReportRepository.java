package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Report;
import com.yildiz.terapinisec.util.ReportSituation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository  extends JpaRepository<Report, Long> {

    Optional<Report> findByReportSituation(ReportSituation reportSituation);
    Optional<Report> findByReportOwnerId(Long userId);
    Optional<Report> findByReportOwnerIdAndReportSituation(Long userId, ReportSituation reportSituation);
    List<Report> findByReportCreatedAt(LocalDateTime reportCreatedAt);
    boolean existsByReportOwnerIdAndReportSituation(Long userId, ReportSituation reportSituation);
}

package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.ReportResponseDto;
import com.yildiz.terapinisec.service.ReportService;
import com.yildiz.terapinisec.util.ReportSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/weekly/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportResponseDto> generateWeeklyReport(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.generatePersonalizedWeeklyReport(userId));
    }

    @PostMapping("/monthly/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportResponseDto> generateMonthlyReport(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.generatePersonalizedMonthlyReport(userId));
    }

    @GetMapping("/type")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelfReport(#reportSituation)")
    public ResponseEntity<ReportResponseDto> getReportByType(@RequestParam ReportSituation reportSituation) {
        return ResponseEntity.ok(reportService.findByReportType(reportSituation));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#userId)")
    public ResponseEntity<ReportResponseDto> getReportByOwnerId(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.findByReportOwnerId(userId));
    }

    @GetMapping("/{userId}/type")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#userId)")
        public ResponseEntity<ReportResponseDto> getReportByOwnerIdAndType(@PathVariable Long userId, @RequestParam ReportSituation reportSituation) {
        return ResponseEntity.ok(reportService.findByReportOwnerIdAndReportType(userId, reportSituation));
    }

    @GetMapping("/date")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponseDto>> getReportByCreatedAt
            (@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt) {
        return ResponseEntity.ok(reportService.findByReportCreatedAt(createdAt));
    }
}
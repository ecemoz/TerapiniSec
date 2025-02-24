package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SleepLogCreateDto;
import com.yildiz.terapinisec.dto.SleepLogDetailedDto;
import com.yildiz.terapinisec.dto.SleepLogResponseDto;
import com.yildiz.terapinisec.security.SecurityService;
import com.yildiz.terapinisec.service.SleepLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sleeplogs")
public class SleepLogController {

    @Autowired
    private SleepLogService sleepLogService;

    @Autowired
    private SecurityService securityService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<SleepLogResponseDto>> getAllSleepLogs() {
        return ResponseEntity.ok(sleepLogService.getAllSleepLogs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSleepLogOwner(#id)")
    public ResponseEntity<SleepLogDetailedDto> getSleepLogById(@PathVariable Long id) {
        return ResponseEntity.ok(sleepLogService.getSleepLogById(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SleepLogResponseDto> createSleeplog(@RequestBody SleepLogCreateDto sleepLogCreateDto) {
        return ResponseEntity.ok(sleepLogService.createSleepLog(sleepLogCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSleepLogOwner(#id)")
    public ResponseEntity<SleepLogResponseDto> updateSleeplog(@RequestBody SleepLogCreateDto sleepLogCreateDto, @PathVariable Long id) {
        return ResponseEntity.ok(sleepLogService.updateSleepLog(id, sleepLogCreateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSleepLogOwner(#id)")
    public ResponseEntity<SleepLogResponseDto> deleteSleeplog(@PathVariable Long id) {
        sleepLogService.deleteSleepLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#userId)")
    public ResponseEntity<Page<SleepLogResponseDto>>getSleepLogByUserId(@PathVariable Long userId, int page, int size) {
        return ResponseEntity.ok(sleepLogService.getSleepLogByUserId(userId ,page, size));
    }

    @GetMapping("/sleepdate")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelfUser()")
    public ResponseEntity<List<SleepLogResponseDto>> getSleepLogBySleepDate(@RequestParam LocalDateTime sleepDate) {
        return ResponseEntity.ok(sleepLogService.findBySleepDate(sleepDate));
    }

    @GetMapping("/{sleeperId}/sleepdate")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#sleeperId)")
    public ResponseEntity<List<SleepLogResponseDto>> getSleepLogBySleeperIdAndSleepDate(@PathVariable Long sleeperId, @RequestParam LocalDateTime sleepDate) {
        return ResponseEntity.ok(sleepLogService.findBySleeperIdAndSleepDate(sleeperId, sleepDate));
    }

    @GetMapping("/{id}/sleepquality")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<List<SleepLogResponseDto>> getSleepLogBySleepQualityAndSleeperId
            (@PathVariable Long id, @RequestParam int sleepQuality) {
        return ResponseEntity.ok(sleepLogService.findBySleepQualityAndSleeperId(sleepQuality, id));
    }

    @GetMapping("/{userId}/details")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#userId)")
    public ResponseEntity<List<SleepLogDetailedDto>> getUserWithDetails( @PathVariable Long userId) {
        return ResponseEntity.ok(sleepLogService.findByUserWithDetails(userId));
    }
}
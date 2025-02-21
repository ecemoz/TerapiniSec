package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.MoodLogCreateDto;
import com.yildiz.terapinisec.dto.MoodLogResponseDto;
import com.yildiz.terapinisec.dto.MoodLogUpdateDto;
import com.yildiz.terapinisec.service.MoodLogService;
import com.yildiz.terapinisec.util.UserMoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/moodlogs")
public class MoodLogController {

    @Autowired
    private MoodLogService moodLogService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MoodLogResponseDto>> getALLMoodLogs() {
        return ResponseEntity.ok(moodLogService.getAllMoodLog());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isMoodLogOwner(#id)")
    public ResponseEntity<MoodLogResponseDto> getMoodLogById(@PathVariable Long id) {
        return ResponseEntity.ok(moodLogService.getMoodLogById(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MoodLogResponseDto> createMoodlog(@RequestBody MoodLogCreateDto moodLogCreateDto) {
        return ResponseEntity.ok(moodLogService.createMoodLog(moodLogCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isMoodLogOwner(#id)")
    public ResponseEntity<MoodLogResponseDto> updateMoodlog(@PathVariable Long id, @RequestBody MoodLogUpdateDto moodLogUpdateDto) {
        return ResponseEntity.ok(moodLogService.updateMoodLog(id, moodLogUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isMoodLogOwner(#id)")
    public ResponseEntity<MoodLogResponseDto> deleteMoodlog(@PathVariable Long id) {
        moodLogService.deleteMoodLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mood")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MoodLogResponseDto>> findByMood(@RequestParam Collection<List<UserMoods>> userMoods) {
        return ResponseEntity.ok(moodLogService.findByMood(userMoods));
    }

    @GetMapping("/count/mood")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST', 'USER')")
    public ResponseEntity<Long> countByMood(@RequestParam List<UserMoods> mood) {
        return ResponseEntity.ok(moodLogService.countByMood(mood));
    }

    @GetMapping("/{userId}/mood")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#userId)")
    public ResponseEntity<MoodLogResponseDto> findByUserIdAndMood(@PathVariable Long userId, @RequestParam Collection< List<UserMoods>> userMoods) {
        return ResponseEntity.ok(moodLogService.findByUserIdAndMood(userId, userMoods));
    }

    @GetMapping("/between")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MoodLogResponseDto>> getMoodLogsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        List<MoodLogResponseDto> response = moodLogService.findByLogDateTimeBetween(startDateTime, endDateTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/between")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#userId)")
    public ResponseEntity<List<MoodLogResponseDto>> findUserIdAndMoodLogsBetweenDates(@PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        List<MoodLogResponseDto> response = moodLogService.findByUserIdAndLogDateTimeBetween(userId,startDateTime,endDateTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/count")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#userId)")
    public ResponseEntity<Long> countByUserIdAndMood(@PathVariable Long userId, @RequestParam List<UserMoods> mood) {
        return ResponseEntity.ok(moodLogService.countByUserIdAndMood(userId, mood));
    }

    @GetMapping("/{userId}/top")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#userId)")
    public ResponseEntity<MoodLogResponseDto> getTopMoodLogByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(moodLogService.findTopByUserIdOrderByMoodDesc(userId));
    }
}
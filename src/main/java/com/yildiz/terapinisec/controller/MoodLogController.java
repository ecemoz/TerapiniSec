package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.MoodLogCreateDto;
import com.yildiz.terapinisec.dto.MoodLogResponseDto;
import com.yildiz.terapinisec.dto.MoodLogUpdateDto;
import com.yildiz.terapinisec.service.MoodLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/moodlogs")
public class MoodLogController {

    @Autowired
    private MoodLogService moodLogService;

    @GetMapping
    public ResponseEntity<List<MoodLogResponseDto>> getALLMoodLogs() {
        return ResponseEntity.ok(moodLogService.getAllMoodLog());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoodLogResponseDto> getMoodLogById(@PathVariable Long id) {
        return ResponseEntity.ok(moodLogService.getMoodLogById(id));
    }

    @PostMapping
    public ResponseEntity<MoodLogResponseDto> createMoodlog(@RequestBody MoodLogCreateDto moodLogCreateDto) {
        return ResponseEntity.ok(moodLogService.createMoodLog(moodLogCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoodLogResponseDto> updateMoodlog(@PathVariable Long id, @RequestBody MoodLogUpdateDto moodLogUpdateDto) {
        return ResponseEntity.ok(moodLogService.updateMoodLog(id, moodLogUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MoodLogResponseDto> deleteMoodlog(@PathVariable Long id) {
        moodLogService.deleteMoodLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mood")
    public ResponseEntity<List<MoodLogResponseDto>> findByMood(@RequestParam String mood) {
        return ResponseEntity.ok(moodLogService.findByMood(mood));
    }

    @GetMapping("/count/mood")
    public ResponseEntity<Long> countByMood(@RequestParam String mood) {
        return ResponseEntity.ok(moodLogService.countByMood(mood));
    }

    @GetMapping("/{userId}/mood")
    public ResponseEntity<MoodLogResponseDto> findByUserIdAndMood(@PathVariable Long userId, @RequestParam String mood) {
        return ResponseEntity.ok(moodLogService.findByUserIdAndMood(userId, mood));
    }

    @GetMapping("/between")
    public ResponseEntity<List<MoodLogResponseDto>> getMoodLogsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        List<MoodLogResponseDto> response = moodLogService.findByLogDateTimeBetween(startDateTime, endDateTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/between")
    public ResponseEntity<List<MoodLogResponseDto>> findUserIdAndMoodLogsBetweenDates(@PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        List<MoodLogResponseDto> response = moodLogService.findByUserIdAndLogDateTimeBetween(userId,startDateTime,endDateTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<Long> countByUserIdAndMood(@PathVariable Long userId, @RequestParam String mood) {
        return ResponseEntity.ok(moodLogService.countByUserIdAndMood(userId, mood));
    }

    @GetMapping("/{userId}/top")
    public ResponseEntity<MoodLogResponseDto> getTopMoodLogByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(moodLogService.findTopByUserIdOrderByMoodDesc(userId));
    }
}
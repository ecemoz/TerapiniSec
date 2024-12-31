package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.MoodLogCreateDto;
import com.yildiz.terapinisec.dto.MoodLogResponseDto;
import com.yildiz.terapinisec.dto.MoodLogUpdateDto;
import com.yildiz.terapinisec.mapper.MoodLogMapper;
import com.yildiz.terapinisec.model.MoodLog;
import com.yildiz.terapinisec.repository.MoodLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoodLogService {

    @Autowired
    private MoodLogRepository moodLogRepository;

    @Autowired
    private MoodLogMapper moodLogMapper;

    public List<MoodLogResponseDto> getAllMoodLog() {
        List<MoodLog> moodLogs = moodLogRepository.findAll();
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public MoodLogResponseDto getMoodLogById(Long id) {
       MoodLog moodLog = moodLogRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("MoodLog not found"));
       return moodLogMapper.toMoodLogResponseDto(moodLog);
    }

    public MoodLogResponseDto createMoodLog(MoodLogCreateDto moodLogCreateDto) {
        MoodLog moodLog = moodLogMapper.toMoodLog(moodLogCreateDto);
        MoodLog savedMoodLog = moodLogRepository.save(moodLog);
        return moodLogMapper.toMoodLogResponseDto(savedMoodLog);
    }

    public MoodLogResponseDto updateMoodLog(Long id, MoodLogUpdateDto moodLogUpdateDto) {
        MoodLog existingMoodLog = moodLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MoodLog not found"));
        moodLogMapper.updateMoodLogFromDto(moodLogUpdateDto, existingMoodLog);
        MoodLog updatedMoodLog = moodLogRepository.save(existingMoodLog);
        return moodLogMapper.toMoodLogResponseDto(updatedMoodLog);
    }

    public void deleteMoodLog(Long id) {
        if (moodLogRepository.existsById(id)) {
            moodLogRepository.deleteById(id);
        } else {
            throw new RuntimeException("MoodLog not found");
        }
    }

    public List<MoodLogResponseDto>findByMood(String mood) {
        List<MoodLog> moodLogs = moodLogRepository.findByMood(mood);
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public long countByMood(String mood) {
        return moodLogRepository.countByMood(mood);
    }

    public MoodLog findByUserId(Long userId) {
        return moodLogRepository.findByUserId(userId);
    }

    public MoodLogResponseDto findByUserIdAndMood(Long userId, String mood) {
        MoodLog moodLog = moodLogRepository.findByUserIdAndMood(userId, mood);
        return moodLogMapper.toMoodLogResponseDto(moodLog);
    }

    public List<MoodLogResponseDto> findByLogDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<MoodLog> moodLogs = moodLogRepository.findByLogDateTimeBetween(startDateTime, endDateTime);
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public List<MoodLogResponseDto> findByUserIdAndLogDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<MoodLog> moodLogs = moodLogRepository.findByUserIdAndLogDateTimeBetween(userId, startDateTime, endDateTime);
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public long countByUserIdAndMood(Long userId, String mood) {
        return moodLogRepository.countByUserIdAndMood(userId, mood);
    }

    public MoodLogResponseDto findTopByUserIdOrderByMoodDesc(Long userId) {
        MoodLog moodLog = moodLogRepository.findTopByUserIdOrderByMoodDesc(userId);
        return moodLogMapper.toMoodLogResponseDto(moodLog);
    }
}
package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.MoodLogCreateDto;
import com.yildiz.terapinisec.dto.MoodLogResponseDto;
import com.yildiz.terapinisec.dto.MoodLogUpdateDto;
import com.yildiz.terapinisec.mapper.MoodLogMapper;
import com.yildiz.terapinisec.model.MoodLog;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.MoodLogRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.UserMoods;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoodLogService {
    private final MoodLogRepository moodLogRepository;
    private final MoodLogMapper moodLogMapper;
    private final UserRepository userRepository;

    public MoodLogService(MoodLogRepository moodLogRepository,
                          MoodLogMapper moodLogMapper,
                          UserRepository userRepository) {
        this.moodLogRepository = moodLogRepository;
        this.moodLogMapper = moodLogMapper;
        this.userRepository = userRepository;
    }

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
        User moodOwner = userRepository.findById(moodLogCreateDto.getMoodOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + moodLogCreateDto.getMoodOwnerId()));

        MoodLog moodLog = moodLogMapper.toMoodLog(moodLogCreateDto, moodOwner);
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

    public List<MoodLogResponseDto> findByMood(Collection<List<UserMoods>> userMoods) {
        List<MoodLog> moodLogs = moodLogRepository.findByUserMoodsIn(userMoods);
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public long countByMood(List<UserMoods> userMoods) {
        return moodLogRepository.countByUserMoodsIn(userMoods);
    }

    public MoodLogResponseDto findByUserIdAndMood(Long userId, Collection<List<UserMoods>> userMoods) {
        MoodLog moodLog = moodLogRepository.findByMoodOwnerIdAndUserMoodsIn(userId, userMoods);
        return moodLogMapper.toMoodLogResponseDto(moodLog);
    }

    public List<MoodLogResponseDto> findByLogDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<MoodLog> moodLogs = moodLogRepository.findByLogDateTimeBetween(startDateTime, endDateTime);
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public List<MoodLogResponseDto> findByUserIdAndLogDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<MoodLog> moodLogs = moodLogRepository.findByMoodOwnerIdAndLogDateTimeBetween(userId, startDateTime, endDateTime);
        return moodLogs.stream()
                .map(moodLogMapper::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }

    public long countByUserIdAndMood(Long userId, List<UserMoods> mood) {
        return moodLogRepository.countByMoodOwnerIdAndUserMoodsIn(userId, mood);
    }

    public MoodLogResponseDto findTopByUserIdOrderByMoodDesc(Long userId) {
        MoodLog moodLog = moodLogRepository.findTopByMoodOwnerIdOrderByUserMoodsDesc(userId);
        return moodLogMapper.toMoodLogResponseDto(moodLog);
    }

    public boolean isUserOwnerOfMoodLog(Long userId, Long moodLogId) {
        return moodLogRepository.isUserOwnerOfMoodLog(userId, moodLogId);
    }
}
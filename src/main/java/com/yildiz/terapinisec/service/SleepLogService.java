package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SleepLogCreateDto;
import com.yildiz.terapinisec.dto.SleepLogDetailedDto;
import com.yildiz.terapinisec.dto.SleepLogResponseDto;
import com.yildiz.terapinisec.mapper.SleepLogMapper;
import com.yildiz.terapinisec.model.SleepLog;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.SleepLogRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SleepLogService {

    private final SleepLogRepository sleepLogRepository;
    private final SleepLogMapper sleepLogMapper;
    private final UserRepository userRepository;

    public SleepLogService(SleepLogRepository sleepLogRepository,
                           SleepLogMapper sleepLogMapper,
                           UserRepository userRepository) {
        this.sleepLogRepository = sleepLogRepository;
        this.sleepLogMapper = sleepLogMapper;
        this.userRepository = userRepository;
    }

    public List<SleepLogResponseDto> getAllSleepLogs() {
        List<SleepLog> sleepLogs = sleepLogRepository.findAll();
        return sleepLogMapper.toSleepLogResponseDtoList(sleepLogs);
    }

    public SleepLogDetailedDto getSleepLogById(Long id) {
        SleepLog sleepLog = sleepLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SleepLog not found"));
        return sleepLogMapper.toSleepLogDetailedDto(sleepLog);
    }

    public SleepLogResponseDto createSleepLog(SleepLogCreateDto sleepLogCreateDto) {

        User sleeper = userRepository.findById(sleepLogCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SleepLog sleepLog = sleepLogMapper.toSleepLog(sleepLogCreateDto, sleeper);
        SleepLog savedSleepLog = sleepLogRepository.save(sleepLog);
        return sleepLogMapper.toSleepLogResponseDto(savedSleepLog);
    }

    public SleepLogResponseDto updateSleepLog(Long id, SleepLogCreateDto sleepLogCreateDto) {
        SleepLog existingSleepLog = sleepLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SleepLog not found"));

        User sleeper = userRepository.findById(sleepLogCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SleepLog updatedSleepLog = sleepLogMapper.toSleepLog(sleepLogCreateDto, sleeper);
        updatedSleepLog.setId(existingSleepLog.getId());
        SleepLog savedSleepLog = sleepLogRepository.save(updatedSleepLog);
        return sleepLogMapper.toSleepLogResponseDto(savedSleepLog);
    }

    public void deleteSleepLog(Long id) {
        if (sleepLogRepository.existsById(id)) {
            sleepLogRepository.deleteById(id);
        } else {
            throw new RuntimeException("SleepLog not found");
        }
    }

    public Page<SleepLogResponseDto> getSleepLogByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SleepLog> sleepLogs = sleepLogRepository.findBySleeperId(userId, pageable);
        return sleepLogs.map(sleepLogMapper::toSleepLogResponseDto);
    }

    public List<SleepLogResponseDto> findBySleepDate(LocalDateTime sleepDate) {
        List<SleepLog> sleepLogs = sleepLogRepository.findBySleepDate(sleepDate);
        return sleepLogMapper.toSleepLogResponseDtoList(sleepLogs);
    }

    public List<SleepLogResponseDto> findBySleeperIdAndSleepDate(Long id, LocalDateTime sleepDate) {
        List<SleepLog> sleepLogs = sleepLogRepository.findBySleeperIdAndSleepDate(id, sleepDate);
        return sleepLogMapper.toSleepLogResponseDtoList(sleepLogs);
    }

    public List<SleepLogResponseDto> findBySleepQualityAndSleeperId(int sleepQuality, Long id) {
        List<SleepLog> sleepLogs = sleepLogRepository.findBySleepQualityValueAndSleeperId(sleepQuality, id);
        return sleepLogMapper.toSleepLogResponseDtoList(sleepLogs);
    }

    public List<SleepLogDetailedDto> findByUserWithDetails(Long userId) {
        List<SleepLog> sleepLogs = sleepLogRepository.findByUserWithDetails(userId);
        return sleepLogMapper.toSleepLogDetailedDtoList(sleepLogs);
    }

    public boolean isUserOwnerOfSleepLog(Long userId, Long sleepLogId) {
        return sleepLogRepository.existsByIdAndSleeperId(sleepLogId, userId);
    }
}
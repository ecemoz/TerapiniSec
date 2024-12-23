package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.SleepLog;
import com.yildiz.terapinisec.repository.SleepLogRepository;
import com.yildiz.terapinisec.util.SleepQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SleepLogService {

    @Autowired
    private SleepLogRepository sleepLogRepository;


    public List<SleepLog> getAllSleepLog() {
        return sleepLogRepository.findAll();
    }

    public Optional<SleepLog> getSleepLogById(Long id) {
        return sleepLogRepository.findById(id);
    }

    public SleepLog createSleepLog(SleepLog sleepLog) {
        sleepLog.setSleepDuration(sleepLog.getSleepDuration());
        sleepLog.setSleepDate(sleepLog.getSleepDate());
        sleepLog.setSleepQuality(sleepLog.getSleepQuality());
        sleepLog.setSleeper(sleepLog.getSleeper());
        return sleepLogRepository.save(sleepLog);
    }

    public SleepLog updateSleepLog(Long id ,SleepLog updatedSleepLog) {
        return sleepLogRepository.findById(id)
                .map(sleepLog -> {
                    sleepLog.setSleepDuration(updatedSleepLog.getSleepDuration());
                    sleepLog.setSleepDate(updatedSleepLog.getSleepDate());
                    sleepLog.setSleepQuality(updatedSleepLog.getSleepQuality());
                    return sleepLogRepository.save(sleepLog);
                })
                .orElseThrow(()->  new RuntimeException ("SleepLog not found"));
    }

    public void deleteSleepLog(Long id) {
        if(sleepLogRepository.existsById(id)) {
            sleepLogRepository.deleteById(id);
        } else {
            throw new RuntimeException ("SleepLog not found");
        }
    }

    public Page<SleepLog> getSleepLogByUserId(Long userId, int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sleepLogRepository.findBySleeperId(userId,pageable);
    }

    public List<SleepLog>findBySleepDate(LocalDateTime sleepDate){
        return sleepLogRepository.findBySleepDate(sleepDate);
    }

    public List<SleepLog>findBySleeperIdAndSleepDate(Long id, LocalDateTime sleepDate) {
        return sleepLogRepository.findBySleeperIdAndSleepDate(id, sleepDate);
    }

    public List<SleepLog>findBySleepQualityAndSleeperId(SleepQuality sleepQuality, Long id) {
        return sleepLogRepository.findBySleepQualityAndSleeperId(sleepQuality, id);
    }

    public List<SleepLog>findByUserWithDetails(Long userId) {
        return sleepLogRepository.findByUserWithDetails(userId);
    }
}
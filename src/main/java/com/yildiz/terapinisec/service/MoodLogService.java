package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.MoodLog;
import com.yildiz.terapinisec.repository.MoodLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MoodLogService {

    @Autowired
    private MoodLogRepository moodLogRepository;

    public List<MoodLog> getAllMoodLog() {
        return moodLogRepository.findAll();
    }

    public Optional<MoodLog> getMoodLogById(Long id) {
        return moodLogRepository.findById(id);
    }

    public MoodLog createMoodLog(MoodLog moodLog) {
        moodLog.setUserMoods(moodLog.getUserMoods());
        moodLog.setDescription(moodLog.getDescription());
        moodLog.setLogDateTime(moodLog.getLogDateTime());
        moodLog.setMoodOwner(moodLog.getMoodOwner());
        return moodLogRepository.save(moodLog);
    }

    public MoodLog updateMoodLog(Long id, MoodLog updatedMoodLog) {
        return moodLogRepository.findById(id)
                .map(moodLog -> {
                    moodLog.setDescription(updatedMoodLog.getDescription());
                    moodLog.setLogDateTime(updatedMoodLog.getLogDateTime());
                    moodLog.setUserMoods(updatedMoodLog.getUserMoods());
                    return moodLogRepository.save(moodLog);
                } )
                .orElseThrow(() -> new RuntimeException("MoodLog not found"));
    }

    public void deleteMoodLog(Long id) {
        if (moodLogRepository.existsById(id)) {
            moodLogRepository.deleteById(id);
        } else {
            throw new RuntimeException("MoodLog not found");
        }
    }

    public List<MoodLog>findByMood(String mood) {
        return moodLogRepository.findByMood(mood);
    }

    public long countByMood(String mood) {
        return moodLogRepository.countByMood(mood);
    }

    public MoodLog findByUserId(Long userId) {
        return moodLogRepository.findByUserId(userId);
    }

    public MoodLog findByUserIdAndMood(Long userId, String mood) {
        return moodLogRepository.findByUserIdAndMood(userId, mood);
    }

    public List<MoodLog> findByLogDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return moodLogRepository.findByLogDateTimeBetween(startDateTime, endDateTime);
    }

    public List<MoodLog> findByUserIdAndLogDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return moodLogRepository.findByUserIdAndLogDateTimeBetween(userId, startDateTime, endDateTime);
    }

    public long countByUserIdAndMood(Long userId, String mood) {
        return moodLogRepository.countByUserIdAndMood(userId, mood);
    }

    public MoodLog findTopByUserIdOrderByMoodDesc(Long userId) {
        return moodLogRepository.findTopByUserIdOrderByMoodDesc(userId);
    }
}
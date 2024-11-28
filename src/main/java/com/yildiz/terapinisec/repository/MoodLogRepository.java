package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {

    MoodLog findByMood(String mood);
    MoodLog countByMood(String mood);
    MoodLog findByUserId(Long userId);
    MoodLog findByUserIdAndMood(Long userId, String mood);
    MoodLog findByLogDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    MoodLog findByUserIdAndLogDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    MoodLog countByUserIdAndMood(Long userId, String mood);
    MoodLog findTopByUserIdOrderByMoodDesc(Long userId);
}

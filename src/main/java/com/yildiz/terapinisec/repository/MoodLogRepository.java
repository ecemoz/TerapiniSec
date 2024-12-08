package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MoodLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {

    List<MoodLog>findByMood(String mood);
    long countByMood(String mood);
    MoodLog findByUserId(Long userId);
    MoodLog findByUserIdAndMood(Long userId, String mood);
    List<MoodLog> findByLogDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<MoodLog> findByUserIdAndLogDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    long countByUserIdAndMood(Long userId, String mood);
    MoodLog findTopByUserIdOrderByMoodDesc(Long userId);

    @Query("SELECT m FROM MoodLog m WHERE m.moodOwner.id = :userId AND m.logDateTime BETWEEN :startDate AND :endDate")
    List<MoodLog> findMoodSummaryByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
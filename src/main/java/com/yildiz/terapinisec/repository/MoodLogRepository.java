package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MoodLog;
import com.yildiz.terapinisec.util.UserMoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {

    List<MoodLog>findByUserMoodsIn(Collection<List<UserMoods>> userMoods);
    long countByUserMoodsIn(List<UserMoods> userMoods);
    MoodLog findByMoodOwnerId(Long userId);
    MoodLog findByMoodOwnerIdAndUserMoodsIn(Long userId, Collection< List<UserMoods>>userMoods );
    List<MoodLog> findByLogDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<MoodLog> findByMoodOwnerIdAndLogDateTimeBetween(Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    long countByMoodOwnerIdAndUserMoodsIn(Long userId, List<UserMoods> userMoods);
    MoodLog findTopByMoodOwnerIdOrderByUserMoodsDesc(Long userId);

    @Query("SELECT m FROM MoodLog m WHERE m.moodOwner.id = :userId AND m.logDateTime BETWEEN :startDate AND :endDate")
    List<MoodLog> findMoodSummaryByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
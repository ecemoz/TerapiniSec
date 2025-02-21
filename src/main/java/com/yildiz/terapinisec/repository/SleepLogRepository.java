package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.SleepLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {

    Page<SleepLog> findBySleeperId(Long id, Pageable pageable);
    List<SleepLog> findBySleepDate(LocalDateTime sleepDate);
    List<SleepLog> findBySleeperIdAndSleepDate(Long id, LocalDateTime sleepDate);
    List<SleepLog> findBySleepQualityValueAndSleeperId(int sleepQualityValue, Long id);

    @Query("SELECT sl FROM SleepLog sl JOIN FETCH sl.sleeper WHERE sl.sleeper.id = :userId")
    List<SleepLog> findByUserWithDetails(@Param("userId") Long userId);

    @Query("SELECT AVG(s.sleepQualityValue) FROM SleepLog s WHERE s.sleeper.id = :userId AND s.sleepDate BETWEEN :startDate AND :endDate")
    Optional<Double> findAverageSleepQualityByUserAndDateRange(@Param("userId") Long userId,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);

    boolean existsByIdAndSleeperId(Long id, Long sleeperId);
}
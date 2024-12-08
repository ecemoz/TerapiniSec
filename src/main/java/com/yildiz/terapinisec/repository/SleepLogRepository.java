package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.SleepLog;
import com.yildiz.terapinisec.util.SleepQuality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {

    Page<SleepLog> findBySleeperId(Long id, Pageable pageable);
    List<SleepLog>findBySleepDate(LocalDateTime sleepDate);
    List<SleepLog>findBySleeperIdAndSleepDate(Long id, LocalDateTime sleepDate);
    List<SleepLog>findBySleepQualityAndSleeperId(SleepQuality sleepQuality, Long id);

    @Query("SELECT sl FROM SleepLog sl JOIN FETCH sl.sleeper WHERE sl.sleeper.id = :userId")
    List<SleepLog> findByUserWithDetails(@Param("userId") Long userId);
}

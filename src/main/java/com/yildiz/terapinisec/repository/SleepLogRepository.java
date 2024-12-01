package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.SleepLog;
import com.yildiz.terapinisec.util.SleepQuality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {

    List<SleepLog>findBySleeperId(Long id);
    List<SleepLog>findBySleepDate(LocalDateTime sleepDate);
    List<SleepLog>findBySleeperIdAndSleepDate(Long id, LocalDateTime sleepDate);
    List<SleepLog>findBySleepQualityAndSleeperId(SleepQuality sleepQuality, Long id);
}
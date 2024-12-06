package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Session;
import com.yildiz.terapinisec.util.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session>findBySessionType(SessionType sessionType);
    List<Session>findBySessionDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Session>findBySessionNameContainingIgnoreCase(String keyword);
}
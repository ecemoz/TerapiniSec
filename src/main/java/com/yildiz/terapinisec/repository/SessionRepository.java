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
    //belli bir tarih girilecek ve o tarihin saat 00-23.59 arası sorgulama listesi döndürülecek servis katmanında...
    List<Session>findBySessionNameContainingIgnoreCase(String keyword);
}

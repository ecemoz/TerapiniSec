package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Session;
import com.yildiz.terapinisec.util.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session>findBySessionType(SessionType sessionType);
    List<Session>findBySessionDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Session>findBySessionNameContainingIgnoreCase(String keyword);

    @Query("SELECT s FROM Session s JOIN FETCH s.participants WHERE s.id = :id")
    Optional<Session> findByIdWithParticipants(@Param("id") Long id);

    @Query("SELECT COUNT(s) > 0 FROM Session s JOIN s.participants p WHERE s.id = :sessionId AND p.id = :userId")
    boolean isUserParticipantOfSession(@Param("userId") Long userId, @Param("sessionId") Long sessionId);

    boolean existsByIdAndSessionOwnerId(Long sessionId, Long sessionOwnerId);
}
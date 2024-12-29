package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {

    List<Participant> findBySessionId(Long sessionId);
    List<Participant>findByJoinedUserId(Long userId);

    @Query("SELECT COUNT(p) FROM Participant p WHERE p.session.id = :sessionId")
    long countByParticipantId(@Param("sessionId") Long sessionId);

    @Query("SELECT p.joinedUser FROM Participant p WHERE p.session.id = :sessionId")
    List<User>findJoinedUserBySessionId(@Param("sessionId") Long sessionId);
}

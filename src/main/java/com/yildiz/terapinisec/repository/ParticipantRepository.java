package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,Long> {

    List<Participant> findBySessionId(Long sessionId);
    List<Participant>findByJoinedUserId(Long userId);
    List<User>findJoinedUserBySessionId(Long sessionId);
    long countBySessionId(Long sessionId);
}

package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public List<Participant>getAllParticipants() {
        return participantRepository.findAll();
    }

    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public List<Participant> findBySessionId(Long sessionId) {
        return participantRepository.findBySessionId(sessionId);
    }

    public List<Participant>findByJoinedUserId(Long userId) {
        return participantRepository.findByJoinedUserId(userId);
    }

    public  List<User>findJoinedUserBySessionId(Long sessionId) {
        return participantRepository.findJoinedUserBySessionId(sessionId);
    }

    public long countBySessionId(Long sessionId) {
        return participantRepository.countBySessionId(sessionId);
    }
}
package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.Session;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.SessionRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.SessionStatus;
import com.yildiz.terapinisec.util.SessionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Session>getAllSessions(){
        return sessionRepository.findAll();
    }

    public Optional <Session> getSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    public Session createSession(Session session) {
        session.setSessionName(session.getSessionName());
        session.setSessionDateTime(session.getSessionDateTime());
        session.setSessionType(session.getSessionType());
        session.setSessionStatus(session.getSessionStatus());
        session.setDurationMinutes(session.getDurationMinutes());
        return sessionRepository.save(session);
    }

    public Session updateSession(Long id ,Session updatedSession) {
        return sessionRepository.findById(id)
                .map(session -> {
                    session.setSessionName(updatedSession.getSessionName());
                    session.setSessionDateTime(updatedSession.getSessionDateTime());
                    session.setSessionType(updatedSession.getSessionType());
                    session.setDurationMinutes(updatedSession.getDurationMinutes());
                    return sessionRepository.save(session);
                })
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    @Transactional
    public Session  startSession(Long sessionId) {
        return updateSessionStatus(sessionId, SessionStatus.ACTIVE);
    }

    @Transactional
    public Session completeSession(Long sessionId) {
        return updateSessionStatus(sessionId,SessionStatus.COMPLETED);
    }

    @Transactional
    public Session cancelSession(Long sessionId) {
        return updateSessionStatus(sessionId,SessionStatus.CANCELLED);
    }

    private Session updateSessionStatus(Long sessionId, SessionStatus newStatus) {
        return sessionRepository.findById(sessionId)
                .map(session -> {
                    validateStatusChange(session.getSessionStatus(),newStatus);
                    session.setSessionStatus(newStatus);
                    return sessionRepository.save(session);
                })
                .orElseThrow(()-> new RuntimeException("Session not found with ID: " +sessionId));
    }

    private void validateStatusChange(SessionStatus currentStatus, SessionStatus newStatus) {
        if (currentStatus ==SessionStatus.COMPLETED || currentStatus ==SessionStatus.CANCELLED) {
            throw new IllegalArgumentException("SessionStatus cannot be changed");
        }
    }

    private void deleteSession(Session session) {
        if(session.getSessionStatus() == SessionStatus.COMPLETED) {
            sessionRepository.delete(session);
        } else if(session.getSessionStatus() == SessionStatus.CANCELLED) {
            sessionRepository.delete(session);
        } else {
            throw new IllegalArgumentException("SessionStatus cannot be changed");
        }
    }
     public List<Session>findBySessionType(SessionType sessionType) {
        return sessionRepository.findBySessionType(sessionType);
     }


     public  List<Session>findBySessionNameContainingIgnoreCase(String keyword) {
        return sessionRepository.findBySessionNameContainingIgnoreCase(keyword);
     }

     public List<Session> getSessionByDate(LocalDateTime date) {
         LocalDateTime startDateTime = date.toLocalDate().atStartOfDay();
         LocalDateTime endDateTime = date.toLocalDate().atTime(23, 59, 59);

         return sessionRepository.findBySessionDateTimeBetween(startDateTime, endDateTime);
     }

     @Transactional
    public void addParticipant (Long sessionId, Participant participant) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " +sessionId));

         session.getParticipants().add(participant);
         sessionRepository.save(session);
     }
}
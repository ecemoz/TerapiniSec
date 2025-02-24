package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SessionCreateDto;
import com.yildiz.terapinisec.dto.SessionDetailedDto;
import com.yildiz.terapinisec.dto.SessionResponseDto;
import com.yildiz.terapinisec.mapper.SessionMapper;
import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.Session;
import com.yildiz.terapinisec.repository.SessionRepository;
import com.yildiz.terapinisec.util.SessionStatus;
import com.yildiz.terapinisec.util.SessionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository,
                          SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
    }

    public List<SessionResponseDto> getAllSessions() {
        List<Session> sessions = sessionRepository.findAll();
        return sessionMapper.toSessionResponseDtoList(sessions);
    }

    public SessionDetailedDto getSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return sessionMapper.toSessionDetailedDto(session);
    }

    public SessionResponseDto createSession(SessionCreateDto sessionCreateDto) {
        Session session = sessionMapper.toSession(sessionCreateDto);
        Session savedSession = sessionRepository.save(session);
        return sessionMapper.toSessionResponseDtoList(List.of(savedSession)).get(0);
    }

    public SessionResponseDto updateSession(Long id, SessionCreateDto sessionCreateDto) {
        Session existingSession = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        sessionMapper.toSession(sessionCreateDto);
        Session updatedSession = sessionRepository.save(existingSession);
        return sessionMapper.toSessionResponseDtoList(List.of(updatedSession)).get(0);
    }

    @Transactional
    public SessionResponseDto startSession(Long sessionId) {
        return updateSessionStatus(sessionId, SessionStatus.ACTIVE);
    }

    @Transactional
    public SessionResponseDto completeSession(Long sessionId) {
        return updateSessionStatus(sessionId, SessionStatus.COMPLETED);
    }

    @Transactional
    public SessionResponseDto cancelSession(Long sessionId) {
        return updateSessionStatus(sessionId, SessionStatus.CANCELLED);
    }

    private SessionResponseDto updateSessionStatus(Long sessionId, SessionStatus newStatus) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        validateStatusChange(session.getSessionStatus());
        session.setSessionStatus(newStatus);
        Session updatedSession = sessionRepository.save(session);

        return sessionMapper.toSessionResponseDtoList(List.of(updatedSession)).get(0);
    }

    private void validateStatusChange(SessionStatus currentStatus) {
        if (currentStatus == SessionStatus.COMPLETED || currentStatus == SessionStatus.CANCELLED) {
            throw new IllegalArgumentException("SessionStatus cannot be changed");
        }
    }

    @Transactional
    public void deleteSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getSessionStatus() == SessionStatus.COMPLETED || session.getSessionStatus() == SessionStatus.CANCELLED) {
            sessionRepository.delete(session);
        } else {
            throw new IllegalArgumentException("Only COMPLETED or CANCELLED sessions can be deleted");
        }
    }


    public List<SessionResponseDto> findBySessionType(SessionType sessionType) {
        List<Session> sessions = sessionRepository.findBySessionType(sessionType);
        return sessionMapper.toSessionResponseDtoList(sessions);
    }


    public List<SessionResponseDto> findBySessionNameContainingIgnoreCase(String keyword) {
        List<Session> sessions = sessionRepository.findBySessionNameContainingIgnoreCase(keyword);
        return sessionMapper.toSessionResponseDtoList(sessions);
    }

    public List<SessionResponseDto> getSessionByDate(LocalDateTime date) {
        LocalDateTime startDateTime = date.toLocalDate().atStartOfDay();
        LocalDateTime endDateTime = date.toLocalDate().atTime(23, 59, 59);

        List<Session> sessions = sessionRepository.findBySessionDateTimeBetween(startDateTime, endDateTime);
        return sessionMapper.toSessionResponseDtoList(sessions);
    }

    @Transactional
    public void addParticipant(Long sessionId, Participant participant) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + sessionId));

        if (session.getParticipants().contains(participant)) {
            throw new IllegalArgumentException("Participant already exists");
        }

        session.getParticipants().add(participant);
        sessionRepository.save(session);
    }

    public SessionDetailedDto getSessionWithParticipants(Long sessionId) {
        Session session = sessionRepository.findByIdWithParticipants(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + sessionId));
        return sessionMapper.toSessionDetailedDto(session);
    }

    public boolean isUserParticipantOfSession(Long userId, Long sessionId) {
        return sessionRepository.isUserParticipantOfSession(sessionId, userId);
    }

    public boolean isUserOwnerOfSession(Long userId, Long sessionId) {
        return sessionRepository.existsByIdAndSessionOwnerId(sessionId, userId);
    }
}
package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.dto.SessionCreateDto;
import com.yildiz.terapinisec.dto.SessionDetailedDto;
import com.yildiz.terapinisec.dto.SessionResponseDto;
import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.Session;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionMapper {

    public Session toSession(SessionCreateDto createDto) {
        if (createDto == null) {
            return null;
        }

        return Session.builder()
                .sessionName(createDto.getSessionName())
                .sessionDateTime(createDto.getSessionDateTime())
                .sessionType(createDto.getSessionType())
                .durationMinutes(createDto.getDurationMinutes())
                .build();
    }

    public SessionResponseDto toSessionResponseDto(Session session) {
        if (session == null) {
            return null;
        }

        return SessionResponseDto.builder()
                .id(session.getId())
                .sessionName(session.getSessionName())
                .sessionDateTime(session.getSessionDateTime())
                .sessionType(session.getSessionType())
                .durationMinutes(session.getDurationMinutes())
                .sessionStatus(session.getSessionStatus())
                .build();
    }

    public SessionDetailedDto toSessionDetailedDto(Session session) {
        if (session == null) {
            return null;
        }

        return SessionDetailedDto.builder()
                .id(session.getId())
                .sessionName(session.getSessionName())
                .sessionDateTime(session.getSessionDateTime())
                .sessionType(session.getSessionType())
                .durationMinutes(session.getDurationMinutes())
                .sessionStatus(session.getSessionStatus())
                .participants(session.getParticipants())
                .build();
    }

    public ParticipantDto toParticipantDto(Participant participant) {
        if (participant == null) {
            return null;
        }

        return ParticipantDto.builder()
                .participantName(participant.getJoinedUser() != null ? participant.getJoinedUser().getUsername() : null)
                .participantEmail(participant.getJoinedUser() != null ? participant.getJoinedUser().getEmail() : null)
                .sessionId(participant.getSession() != null ? participant.getSession().getId() : null)
                .build();
    }

    public List<SessionResponseDto> toSessionResponseDtoList(List<Session> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return List.of();
        }

        return sessions.stream()
                .map(this::toSessionResponseDto)
                .collect(Collectors.toList());
    }

    public List<SessionDetailedDto> toSessionDetailedDtoList(List<Session> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return List.of();
        }

        return sessions.stream()
                .map(this::toSessionDetailedDto)
                .collect(Collectors.toList());
    }

    public List<ParticipantDto> toParticipantDtoList(List<Participant> participants) {
        if (participants == null || participants.isEmpty()) {
            return List.of();
        }

        return participants.stream()
                .map(this::toParticipantDto)
                .collect(Collectors.toList());
    }
}

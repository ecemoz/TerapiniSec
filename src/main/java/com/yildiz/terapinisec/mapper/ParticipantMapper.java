package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.Session;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParticipantMapper {

    public static ParticipantDto toParticipantDto(Participant participant) {
        if (participant == null) {
            return null;
        }

        return ParticipantDto.builder()
                .id(participant.getId())
                .joinedAt(participant.getJoinedAt())
                .participantName(participant.getJoinedUser() != null ? participant.getJoinedUser().getUsername() : null)
                .participantEmail(participant.getJoinedUser() != null ? participant.getJoinedUser().getEmail() : null)
                .sessionId(participant.getSession() != null ? participant.getSession().getId() : null)
                .build();
    }

    public static Participant toParticipant(ParticipantDto dto) {
        if (dto == null) {
            return null;
        }

        return Participant.builder()
                .id(dto.getId())
                .joinedAt(dto.getJoinedAt())
                .build();
    }

    public static Participant toParticipant(ParticipantDto dto, User user, Session session) {
        if (dto == null) {
            return null;
        }

        return Participant.builder()
                .id(dto.getId())
                .joinedAt(dto.getJoinedAt())
                .joinedUser(user)
                .session(session)
                .build();
    }

    public static List<ParticipantDto> toParticipantDtoList(List<Participant> participants) {
        if (participants == null || participants.isEmpty()) {
            return List.of();
        }

        return participants.stream()
                .map(ParticipantMapper::toParticipantDto)
                .collect(Collectors.toList());
    }

    public static List<Participant> toParticipantList(List<ParticipantDto> dtos, User user, Session session) {
        if (dtos == null || dtos.isEmpty()) {
            return List.of();
        }

        return dtos.stream()
                .map(dto -> toParticipant(dto, user, session))
                .collect(Collectors.toList());
    }
}
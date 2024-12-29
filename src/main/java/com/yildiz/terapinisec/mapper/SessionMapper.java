package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.dto.SessionCreateDto;
import com.yildiz.terapinisec.dto.SessionDetailedDto;
import com.yildiz.terapinisec.dto.SessionResponseDto;
import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    Session toSession(SessionCreateDto sessionCreateDto);

    @Mapping(source = "participants" , target = " participants")
    SessionDetailedDto toSessionDetailedDto(Session session);

    @Mapping(source = "joinedUser.username" , target = "participantName")
    @Mapping(source = "joinedUser.email" , target = "participantEmail")
    ParticipantDto toParticipantDto(Participant participant);

    List<SessionResponseDto> toSessionResponseDtoList(List<Session> sessions);

    List<SessionDetailedDto> toSessionDetailedDtoList(List<Session> sessions);

    List<ParticipantDto> toParticipantDtoList(List<Participant> participants);
}

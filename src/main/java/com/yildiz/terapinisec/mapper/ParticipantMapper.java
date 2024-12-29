package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    @Mapping(source = "joinedUser.username" , target = "participantName")
    @Mapping(source = "joinedUser.email" , target = "participantEmail")
    @Mapping(source = "participant.id" , target = "sessionId")
    ParticipantDto toParticipantDto(Participant participant);

    List<ParticipantDto> toParticipantDtoList(List<Participant> participants);

    @Mapping(source = "participantName"  , target = "joinedUser.username")
    @Mapping(source = "participantEmail"  , target = "joinedUser.email")
    @Mapping(source = "sessionId" , target = "participant.id")
    Participant toParticipant(ParticipantDto participantDto);
}
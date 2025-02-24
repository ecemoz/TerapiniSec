package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.mapper.ParticipantMapper;
import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    public ParticipantService(ParticipantRepository participantRepository,
                              ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    public List<ParticipantDto> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participantMapper.toParticipantDtoList(participants);
    }

    public ParticipantDto getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        return participantMapper.toParticipantDto(participant);
    }

    public ParticipantDto createParticipant(ParticipantDto participantDto) {
        Participant participant = participantMapper.toParticipant(participantDto);
        Participant savedParticipant = participantRepository.save(participant);
        return participantMapper.toParticipantDto(savedParticipant);
    }

    public List<ParticipantDto> findBySessionId(Long sessionId) {
        List<Participant> participants = participantRepository.findBySessionId(sessionId);
        return participantMapper.toParticipantDtoList(participants);
    }

    public List<ParticipantDto> findByJoinedUserId(Long userId) {
        List<Participant> participants = participantRepository.findByJoinedUserId(userId);
        return participantMapper.toParticipantDtoList(participants);
    }

    public List<User> findJoinedUserBySessionId ( Long sessionId ){
        return participantRepository.findJoinedUserBySessionId(sessionId);
    }

    public long countBySessionId (Long sessionId){
        return participantRepository.countByParticipantId(sessionId);
        }
}
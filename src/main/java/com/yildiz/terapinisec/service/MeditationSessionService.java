package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.MeditationSessionCreateDto;
import com.yildiz.terapinisec.dto.MeditationSessionResponseDto;
import com.yildiz.terapinisec.mapper.MeditationSessionMapper;
import com.yildiz.terapinisec.model.MeditationSession;
import com.yildiz.terapinisec.repository.MeditationSessionRepository;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeditationSessionService {

    @Autowired
    private MeditationSessionRepository meditationSessionRepository;

    @Autowired
    private MeditationSessionMapper meditationSessionMapper;

    public List<MeditationSessionResponseDto> getAllMeditationSession() {
        List<MeditationSession> sessions = meditationSessionRepository.findAll();
        return sessions.stream()
                .map(meditationSessionMapper::toMeditationSessionResponseDto)
                .collect(Collectors.toList());
    }

    public MeditationSessionResponseDto getMeditationSessionById(Long id) {
        MeditationSession session = meditationSessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MeditationSession not found"));
        return meditationSessionMapper.toMeditationSessionResponseDto(session);
    }

    public MeditationSessionResponseDto createMeditationSession(MeditationSessionCreateDto meditationSessionCreateDto) {
       MeditationSession session = meditationSessionMapper.toMeditationSession(meditationSessionCreateDto);
       MeditationSession savedSession = meditationSessionRepository.save(session);
       return meditationSessionMapper.toMeditationSessionResponseDto(savedSession);
    }

    public  List<MeditationSessionResponseDto>findByMeditatorId(Long id) {
        List<MeditationSession> sessions = meditationSessionRepository.findByMeditatorId(id);
        return sessions.stream()
                .map(meditationSessionMapper::toMeditationSessionResponseDto)
                .collect(Collectors.toList());
    }

    public  int countByMeditationId (Long id) {
        return meditationSessionRepository.countByMeditationId(id);
    }

    public List<MeditationSessionResponseDto>findByMeditationTitleContaining (String keyword) {
        List<MeditationSession> sessions = meditationSessionRepository.findByMeditationTitleContaining(keyword);
        return sessions.stream()
                .map(meditationSessionMapper::toMeditationSessionResponseDto)
                .collect(Collectors.toList());
    }

    public List<MeditationSessionResponseDto>findByMeditatorIdAndMeditationContentType(Long userId, MeditationSessionType type) {
        List<MeditationSession> sessions = meditationSessionRepository.findByMeditatorIdAndMeditationContentType(userId, type);
        return sessions.stream()
                .map(meditationSessionMapper::toMeditationSessionResponseDto)
                .collect(Collectors.toList());
    }
}
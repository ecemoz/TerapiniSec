package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.MeditationSession;
import com.yildiz.terapinisec.repository.MeditationSessionRepository;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MeditationSessionService {

    @Autowired
    private MeditationSessionRepository meditationSessionRepository;

    public List<MeditationSession> getAllMeditationSession() {
        return meditationSessionRepository.findAll();
    }

    public Optional<MeditationSession> getMeditationSessionById(Long id) {
        return meditationSessionRepository.findById(id);
    }

    public MeditationSession createMeditationSession(MeditationSession meditationSession) {
        return meditationSessionRepository.save(meditationSession);
    }

    public  List<MeditationSession>findByMeditatorId(Long id) {
        return meditationSessionRepository.findByMeditatorId(id);
    }

    public  int countByMeditationId (Long id) {
        return meditationSessionRepository.countByMeditationId(id);
    }

    public List<MeditationSession>findByMeditationTitleContaining (String keyword) {
        return meditationSessionRepository.findByMeditationTitleContaining(keyword);
    }

    public List<MeditationSession>findByMeditatorIdAndMeditationContentType(Long userId, MeditationSessionType type) {
        return meditationSessionRepository.findByMeditatorIdAndMeditationContentType(userId, type);
    }
}
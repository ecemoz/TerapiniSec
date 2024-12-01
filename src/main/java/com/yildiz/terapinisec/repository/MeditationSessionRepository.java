package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MeditationSession;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MeditationSessionRepository extends JpaRepository<MeditationSession, Long> {

    List<MeditationSession>findByMeditatorId(Long id);
    int countByMeditationId (Long id);
    List<MeditationSession>findByMeditationTitleContaining (String keyword);
    List<MeditationSession>findByMeditatorIdAndMeditationContentType(Long userId, MeditationSessionType type);
}

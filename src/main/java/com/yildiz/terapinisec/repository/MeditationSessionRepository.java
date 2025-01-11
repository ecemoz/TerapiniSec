package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MeditationContent;
import com.yildiz.terapinisec.model.MeditationSession;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MeditationSessionRepository extends JpaRepository<MeditationSession, Long> {

    List<MeditationSession>findByMeditatorId(Long id);
    int countByMeditator_Id(Long userId);
    List<MeditationSession> findByMeditatorIdAndMeditationContent_MeditationSessionType(Long userId, MeditationSessionType type);
}

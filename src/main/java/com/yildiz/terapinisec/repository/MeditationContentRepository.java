package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MeditationContent;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MeditationContentRepository  extends JpaRepository<MeditationContent, Long> {

    List<MeditationContent>findByIsPublicTrue();
    List<MeditationContent>findByMeditationSessionType(MeditationSessionType type);
    List<MeditationContent>findByIsPublicFalse();
    List<MeditationContent>findByCreatedById(Long userId);
}

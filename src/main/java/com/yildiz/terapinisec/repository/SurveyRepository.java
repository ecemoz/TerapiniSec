package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findByTitleContaining(String keyword);
    List<Survey>findByCreatedById(Long userId);
}

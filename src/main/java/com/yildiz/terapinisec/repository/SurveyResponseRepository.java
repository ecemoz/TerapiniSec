package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    List<SurveyResponse>findByResponsedById(Long userId);
    List<SurveyResponse>findBySurveyId(Long surveyId);
    List<SurveyResponse>findByResponsedByIdAndSurveyId(Long userId, Long surveyId);
    List<SurveyResponse> findBySurveyIdOrderBySubmittedDateDesc(Long surveyId);
    boolean  existsBySurveyIdAndUserId(Long surveyId , Long userId);
}

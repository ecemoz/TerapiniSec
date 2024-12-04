package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    public List<Survey>getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    public Survey createSurvey(Survey survey) {
        survey.setTitle(survey.getTitle());
        survey.setDescription(survey.getDescription());
        return surveyRepository.save(survey);
    }

    public Survey updateSurvey(Long id , Survey updatedSurvey) {
        return surveyRepository.findById(id)
                .map(survey -> {
                    survey.setTitle(updatedSurvey.getTitle());
                    survey.setDescription(updatedSurvey.getDescription());
                    return surveyRepository.save(survey);
                })
                .orElseThrow(()-> new RuntimeException("Survey not found."));
    }

    public void deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Survey not found.");
        }
    }

    public List<Survey> findByTitleContaining(String keyword){
        return surveyRepository.findByTitleContaining(keyword);
    }

    public List<Survey>findByUserId(Long userId){
        return surveyRepository.findByUserId(userId);
    }
}
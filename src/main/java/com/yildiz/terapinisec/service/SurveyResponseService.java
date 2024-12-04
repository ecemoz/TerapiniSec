package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.repository.SurveyResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyResponseService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    public List<SurveyResponse>getAllSurveyResponses(){
        return surveyResponseRepository.findAll();
    }

    public Optional<SurveyResponse> getSurveyResponseById(Long id){
        return surveyResponseRepository.findById(id);
    }

    public SurveyResponse createSurveyResponse(SurveyResponse surveyResponse){
       return surveyResponseRepository.save(surveyResponse);
    }

    public  List<SurveyResponse>findByResponsedById(Long userId){
         return surveyResponseRepository.findByResponsedById(userId);
    }

    public List<SurveyResponse>findBySurveyId(Long surveyId){
        return surveyResponseRepository.findBySurveyId(surveyId);
    }

    public List<SurveyResponse>findByResponsedByIdAndSurveyId(Long userId, Long surveyId){
        return surveyResponseRepository.findByResponsedByIdAndSurveyId(userId, surveyId);
    }

    public List<SurveyResponse>findBySurveyIdOrderBySubmittedDateDesc(Long surveyId){
        return surveyResponseRepository.findBySurveyIdOrderBySubmittedDateDesc(surveyId);
    }
}
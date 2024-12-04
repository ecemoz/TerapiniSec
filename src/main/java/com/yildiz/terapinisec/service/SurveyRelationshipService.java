package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.SurveyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyRelationshipService {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyResponseService surveyResponseService;

    public List<SurveyResponse>getResponsesBySurvey(Long surveyId) {
        return surveyResponseService.findBySurveyId(surveyId);
    }

    public SurveyResponse addResponseToSurvey(Long surveyId ,SurveyResponse surveyResponse) {
        return surveyService.getSurveyById(surveyId)
                .map(survey -> {
                    surveyResponse.setSurvey(survey);
                    return surveyResponseService.createSurveyResponse(surveyResponse);
                })
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    public void deleteSurveyWithResponses(Long surveyId) {
        List<SurveyResponse> responses = surveyResponseService.findBySurveyId(surveyId);
        surveyResponseService.getAllSurveyResponses().removeAll(responses);
        surveyService.deleteSurvey(surveyId);
    }
}
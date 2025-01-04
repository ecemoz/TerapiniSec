package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.mapper.SurveyResponseMapper;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.repository.SurveyResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyRelationshipService {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private SurveyResponseMapper surveyResponseMapper;

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    public List<SurveyResponsePostDto> getResponsesBySurvey(Long surveyId) {
        return surveyResponseService.findBySurveyId(surveyId);
    }

    public SurveyResponsePostDto addResponseToSurvey(Long surveyId, SurveyResponseCreateDto surveyResponseCreateDto) {
        Survey survey = surveyService.getSurveyEntityById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        SurveyResponse surveyResponse = surveyResponseMapper.toSurveyResponse(surveyResponseCreateDto);
        surveyResponse.setSurvey(survey);

        SurveyResponse createdResponse = surveyResponseRepository.save(surveyResponse);
        return surveyResponseMapper.toSurveyResponseResponseDto(createdResponse);
    }

    public void deleteSurveyWithResponses(Long surveyId) {
        List<SurveyResponsePostDto> surveyResponses = surveyResponseService.findBySurveyId(surveyId);
        surveyResponses.forEach(surveyResponse -> surveyResponseService.deleteSurveyResponse(surveyResponse.getId()));
        surveyService.deleteSurvey(surveyId);
    }
}
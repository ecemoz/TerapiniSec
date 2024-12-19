package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyPostDto;
import com.yildiz.terapinisec.mapper.SurveyMapper;
import com.yildiz.terapinisec.mapper.SurveyResponseMapper;
import com.yildiz.terapinisec.model.SurveyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyRelationshipService {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private SurveyResponseMapper surveyResponseMapper;

    public List<SurveyPostDto>getResponsesBySurvey(Long surveyId) {
        List<SurveyResponse> responses = surveyResponseService.findBySurveyId(surveyId);
        return responses.stream()
                .map(surveyResponseMapper::toSurveyResponseDto)
                .collect(Collectors.toList());
    }

    public SurveyPostDto addResponseToSurvey(Long surveyId , SurveyResponseCreateDto surveyResponseCreateDto) {
        return surveyService.getSurveyById(surveyId)
                .map(surveyResponse -> {
                    SurveyResponse surveyResponse = surveyResponseMapper.toSurveyResponse(surveyResponseCreateDto);
                    surveyResponse.setSurvey(surveyMapper.toSurvey(survey));
                    SurveyResponse createdResponse = surveyResponseService.createSurveyResponse(surveyResponse);
                    return surveyResponseMapper.toSurveyResponseDto(createdResponse);
                })
                .orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    public void deleteSurveyWithResponses(Long surveyId) {
        List<SurveyResponse> responses = surveyResponseService.findBySurveyId(surveyId);
        responses.forEach(surveyResponse -> surveyResponseService.deleteSurveyResponse(surveyResponse.getId()));
        surveyService.deleteSurvey(surveyId);
    }
}
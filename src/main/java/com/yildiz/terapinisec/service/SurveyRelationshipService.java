package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.mapper.SurveyResponseMapper;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.repository.SurveyResponseRepository;
import com.yildiz.terapinisec.repository.SurveyRepository;
import com.yildiz.terapinisec.repository.UserRepository;
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

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;


    public List<SurveyResponsePostDto> getResponsesBySurvey(Long surveyId) {
        return surveyResponseService.findBySurveyId(surveyId);
    }

    public SurveyResponsePostDto addResponseToSurvey(Long surveyId, SurveyResponseCreateDto surveyResponseCreateDto) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        SurveyResponse surveyResponse = surveyResponseMapper.toSurveyResponse(
                surveyResponseCreateDto,
                userRepository.findById(surveyResponseCreateDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found")),
                survey);

        SurveyResponse createdResponse = surveyResponseRepository.save(surveyResponse);

        return surveyResponseMapper.toSurveyResponsePostDto(createdResponse);
    }

    public void deleteSurveyWithResponses(Long surveyId) {
        List<SurveyResponsePostDto> surveyResponses = surveyResponseService.findBySurveyId(surveyId);
        surveyResponses.forEach(surveyResponse -> surveyResponseService.deleteSurveyResponse(surveyResponse.getId()));
        surveyService.deleteSurvey(surveyId);
    }
}

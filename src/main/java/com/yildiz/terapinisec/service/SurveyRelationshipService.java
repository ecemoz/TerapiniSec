package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.mapper.SurveyResponseMapper;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.repository.SurveyResponseRepository;
import com.yildiz.terapinisec.repository.SurveyRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SurveyRelationshipService {

    private final SurveyService surveyService;
    private final SurveyResponseService surveyResponseService;
    private final SurveyResponseMapper surveyResponseMapper;
    private final SurveyResponseRepository surveyResponseRepository;
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    public SurveyRelationshipService(SurveyService surveyService,
                                   SurveyResponseService surveyResponseService,
                                   SurveyResponseMapper surveyResponseMapper,
                                   SurveyResponseRepository surveyResponseRepository,
                                   SurveyRepository surveyRepository,
                                   UserRepository userRepository) {
        this.surveyService = surveyService;
        this.surveyResponseService = surveyResponseService;
        this.surveyResponseMapper = surveyResponseMapper;
        this.surveyResponseRepository = surveyResponseRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }


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

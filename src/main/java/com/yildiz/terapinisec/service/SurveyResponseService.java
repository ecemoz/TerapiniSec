package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.mapper.SurveyResponseMapper;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.SurveyRepository;
import com.yildiz.terapinisec.repository.SurveyResponseRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyResponseService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SurveyResponseMapper surveyResponseMapper;

    public List<SurveyResponsePostDto> getAllSurveyResponses() {
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findAll();
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponsePostDto)
                .collect(Collectors.toList());
    }

    public SurveyResponsePostDto getSurveyResponseById(Long id) {
        SurveyResponse surveyResponse = surveyResponseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey Response Not Found"));
        return surveyResponseMapper.toSurveyResponsePostDto(surveyResponse);
    }

    public SurveyResponsePostDto createSurveyResponse(SurveyResponseCreateDto surveyResponseCreateDto) {
        User user = userRepository.findById(surveyResponseCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Survey survey = surveyRepository.findById(Long.valueOf(surveyResponseCreateDto.getSurveyId()))
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        SurveyResponse surveyResponse = surveyResponseMapper.toSurveyResponse(surveyResponseCreateDto, user, survey);
        SurveyResponse savedSurveyResponse = surveyResponseRepository.save(surveyResponse);

        return surveyResponseMapper.toSurveyResponsePostDto(savedSurveyResponse);
    }

    public List<SurveyResponsePostDto> findByResponsedById(Long userId) {
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findByResponsedById(userId);
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponsePostDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponsePostDto> findBySurveyId(Long surveyId) {
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findBySurveyId(surveyId);
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponsePostDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponsePostDto> findByResponsedByIdAndSurveyId(Long userId, Long surveyId) {
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findByResponsedByIdAndSurveyId(userId, surveyId);
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponsePostDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponsePostDto> findBySurveyIdOrderBySubmittedDateDesc(Long surveyId) {
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findBySurveyIdOrderBySubmittedDateDesc(surveyId);
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponsePostDto)
                .collect(Collectors.toList());
    }

    public void deleteSurveyResponse(Long id) {
        if (surveyResponseRepository.existsById(id)) {
            surveyResponseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Survey Response not found.");
        }
    }
}
package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.mapper.SurveyResponseMapper;
import com.yildiz.terapinisec.model.SurveyResponse;
import com.yildiz.terapinisec.repository.SurveyResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyResponseService {

    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    @Autowired
    private SurveyResponseMapper surveyResponseMapper;

    public List<SurveyResponsePostDto>getAllSurveyResponses(){
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findAll();
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponseResponseDto)
                .collect(Collectors.toList());
    }

    public SurveyResponsePostDto getSurveyResponseById(Long id){
        SurveyResponse surveyResponse = surveyResponseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey Response Not Found"));
        return surveyResponseMapper.toSurveyResponseResponseDto(surveyResponse);
    }

    public SurveyResponse createSurveyResponse(SurveyResponse surveyResponse) {
        return surveyResponseRepository.save(surveyResponse);
    }

    public  List<SurveyResponsePostDto>findByResponsedById(Long userId){
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findByResponsedById(userId);
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponseResponseDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponsePostDto> findBySurveyId(Long surveyId){
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findBySurveyId(surveyId);
        return surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponseResponseDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponsePostDto>findByResponsedByIdAndSurveyId(Long userId, Long surveyId){
        List<SurveyResponse>surveyResponses  = surveyResponseRepository.findByResponsedByIdAndSurveyId(userId, surveyId);
        return  surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponseResponseDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponsePostDto>findBySurveyIdOrderBySubmittedDateDesc(Long surveyId){
        List<SurveyResponse> surveyResponses = surveyResponseRepository.findBySurveyIdOrderBySubmittedDateDesc(surveyId);
        return  surveyResponses.stream()
                .map(surveyResponseMapper::toSurveyResponseResponseDto)
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
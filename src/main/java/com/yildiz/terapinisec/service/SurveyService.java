package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponseDto;
import com.yildiz.terapinisec.dto.SurveyUpdateDto;
import com.yildiz.terapinisec.mapper.SurveyMapper;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.repository.SurveyRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyMapper surveyMapper;
    @Autowired
    private UserRepository userRepository;

    public List<SurveyResponseDto>getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream()
                .map(surveyMapper::toSurveyResponseDto)
                .collect(Collectors.toList());
    }

    public SurveyResponseDto getSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        return surveyMapper.toSurveyResponseDto(survey);
    }

    public SurveyResponseDto createSurvey(SurveyCreateDto surveyCreateDto) {
        Survey survey = surveyMapper.toSurvey(surveyCreateDto);
        survey.setCreatedBy(userRepository.findById(surveyCreateDto.getCreatedById())
        .orElseThrow(() -> new RuntimeException("User not found")));
        Survey savedSurvey = surveyRepository.save(survey);
        return surveyMapper.toSurveyResponseDto(savedSurvey);
    }

    public SurveyResponseDto updateSurvey(Long id , SurveyUpdateDto surveyUpdateDto) {
       Survey survey = surveyRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Survey not found"));
       surveyMapper.updateSurveyFromDto(surveyUpdateDto, survey);
       Survey updatedSurvey = surveyRepository.save(survey);
       return surveyMapper.toSurveyResponseDto(updatedSurvey);
    }

    public void deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Survey not found.");
        }
    }

    public List<SurveyResponseDto> findByTitleContaining(String keyword){
        List<Survey>surveys = surveyRepository.findByTitleContaining(keyword);
        return surveys.stream()
                .map(surveyMapper::toSurveyResponseDto)
                .collect(Collectors.toList());
    }

    public List<SurveyResponseDto>findByUserId(Long userId){
        List<Survey> surveys = surveyRepository.findByUserId(userId);
        return surveys.stream()
                .map(surveyMapper:: toSurveyResponseDto)
                .collect(Collectors.toList());
    }
}
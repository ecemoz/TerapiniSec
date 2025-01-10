package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.SurveyCreateDto;
import com.yildiz.terapinisec.dto.SurveyPostDto;
import com.yildiz.terapinisec.dto.SurveyUpdateDto;
import com.yildiz.terapinisec.mapper.SurveyMapper;
import com.yildiz.terapinisec.model.Survey;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.SurveyRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private UserRepository userRepository;

    public List<SurveyPostDto>getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream()
                .map(surveyMapper::toSurveyPostDto)
                .collect(Collectors.toList());
    }

    public SurveyPostDto getSurveyById(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        return surveyMapper.toSurveyPostDto(survey);
    }

    public SurveyPostDto createSurvey(SurveyCreateDto surveyCreateDto) {
        User user = userRepository.findById(surveyCreateDto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Survey survey = surveyMapper.toSurvey(surveyCreateDto, user);
        Survey savedSurvey = surveyRepository.save(survey);
        return surveyMapper.toSurveyPostDto(savedSurvey);
    }


    public SurveyPostDto updateSurvey(Long id , SurveyUpdateDto surveyUpdateDto) {
       Survey survey = surveyRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Survey not found"));
       surveyMapper.updateSurveyFromDto(surveyUpdateDto, survey);
       Survey updatedSurvey = surveyRepository.save(survey);
       return surveyMapper.toSurveyPostDto(updatedSurvey);
    }

    public void deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Survey not found.");
        }
    }

    public List<SurveyPostDto> findByTitleContaining(String keyword){
        List<Survey>surveys = surveyRepository.findByTitleContaining(keyword);
        return surveys.stream()
                .map(surveyMapper::toSurveyPostDto)
                .collect(Collectors.toList());
    }

    public List<SurveyPostDto>findSurveyByUserId(Long userId){
        List<Survey> surveys = surveyRepository.findByUserId(userId);
        return surveys.stream()
                .map(surveyMapper:: toSurveyPostDto)
                .collect(Collectors.toList());
    }

    public Optional<Survey> getSurveyEntityById(Long id) {
        return surveyRepository.findById(id);
    }
}
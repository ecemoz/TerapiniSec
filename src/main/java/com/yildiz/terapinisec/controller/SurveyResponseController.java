package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.service.SurveyResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/surveyresponses")
public class SurveyResponseController {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @GetMapping
    public ResponseEntity<List<SurveyResponsePostDto>> getAllSurveyResponses() {
        return ResponseEntity.ok(surveyResponseService.getAllSurveyResponses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponsePostDto> getSurveyResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyResponseService.getSurveyResponseById(id));
    }

    @PostMapping
    public ResponseEntity<SurveyResponsePostDto> createSurveyResponse(@RequestBody SurveyResponseCreateDto surveyResponseCreateDto) {
        return ResponseEntity.ok(surveyResponseService.createSurveyResponse(surveyResponseCreateDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SurveyResponsePostDto>> getResponsedById(@PathVariable Long userId) {
        return ResponseEntity.ok(surveyResponseService.findByResponsedById(userId));
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<List<SurveyResponsePostDto>> findBySurveyId(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyResponseService.findBySurveyId(surveyId));
    }

    @GetMapping("/{userId}/{surveyId}")
    public ResponseEntity<List<SurveyResponsePostDto>> findByResponsedByIdAndSurveyId(@PathVariable Long userId, @PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyResponseService.findByResponsedByIdAndSurveyId(userId, surveyId));
    }

    @GetMapping("/{surveyId}/ord?submitteddate")
    public ResponseEntity<List<SurveyResponsePostDto>> findBySurveyIdSubmittedDate(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyResponseService.findBySurveyIdOrderBySubmittedDateDesc(surveyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurveyResponse(@PathVariable Long id) {
        surveyResponseService.deleteSurveyResponse(id);
        return ResponseEntity.noContent().build();
    }
}
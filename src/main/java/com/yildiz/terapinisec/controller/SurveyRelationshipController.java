package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.service.SurveyRelationshipService;
import com.yildiz.terapinisec.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/surveyrelationship")
public class SurveyRelationshipController {

   @Autowired
   private SurveyRelationshipService surveyRelationshipService;

   @Autowired
   private SurveyService surveyService;

    @GetMapping("/surveyId")
    public ResponseEntity<List<SurveyResponsePostDto>> getResponsesBySurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyRelationshipService.getResponsesBySurvey(surveyId));
    }

    @PostMapping
    public ResponseEntity<SurveyResponsePostDto> addResponseToSurvey(@PathVariable Long surveyId, @RequestBody SurveyResponseCreateDto surveyResponseCreateDto) {
        return ResponseEntity.ok(surveyRelationshipService.addResponseToSurvey(surveyId, surveyResponseCreateDto));
    }

    @DeleteMapping("/{surveyId}/with-responses")
    public ResponseEntity<Void> deleteSurveyWithResponses(@PathVariable Long surveyId) {
        surveyRelationshipService.deleteSurveyWithResponses(surveyId);
        return ResponseEntity.noContent().build();
    }
}
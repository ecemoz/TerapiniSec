package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SurveyResponseCreateDto;
import com.yildiz.terapinisec.dto.SurveyResponsePostDto;
import com.yildiz.terapinisec.security.SecurityService;
import com.yildiz.terapinisec.service.SurveyResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/surveyresponses")
public class SurveyResponseController {

    @Autowired
    private SurveyResponseService surveyResponseService;

    @Autowired
    private SecurityService securityService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<List<SurveyResponsePostDto>> getAllSurveyResponses() {
        return ResponseEntity.ok(surveyResponseService.getAllSurveyResponses());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<SurveyResponsePostDto> getSurveyResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyResponseService.getSurveyResponseById(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SurveyResponsePostDto> createSurveyResponse(@RequestBody SurveyResponseCreateDto surveyResponseCreateDto) {
        return ResponseEntity.ok(surveyResponseService.createSurveyResponse(surveyResponseCreateDto));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<List<SurveyResponsePostDto>> getResponsedById(@PathVariable Long userId) {
        return ResponseEntity.ok(surveyResponseService.findByResponsedById(userId));
    }

    @GetMapping("/{surveyId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.hasUserRespondedToSurvey(#surveyId)")
    public ResponseEntity<List<SurveyResponsePostDto>> findBySurveyId(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyResponseService.findBySurveyId(surveyId));
    }

    @GetMapping("/{userId}/{surveyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<List<SurveyResponsePostDto>> findByResponsedByIdAndSurveyId(@PathVariable Long userId, @PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyResponseService.findByResponsedByIdAndSurveyId(userId, surveyId));
    }

    @GetMapping("/{surveyId}/ord?submitteddate")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<List<SurveyResponsePostDto>> findBySurveyIdSubmittedDate(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyResponseService.findBySurveyIdOrderBySubmittedDateDesc(surveyId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<Void> deleteSurveyResponse(@PathVariable Long id) {
        surveyResponseService.deleteSurveyResponse(id);
        return ResponseEntity.noContent().build();
    }
}
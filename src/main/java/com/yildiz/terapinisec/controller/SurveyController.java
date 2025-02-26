package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SurveyCreateDto;
import com.yildiz.terapinisec.dto.SurveyPostDto;
import com.yildiz.terapinisec.dto.SurveyUpdateDto;
import com.yildiz.terapinisec.service.SurveyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SurveyPostDto>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<SurveyPostDto> getSurveyById(@PathVariable  Long id) {
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PSYCHOLOGIST')")
    public ResponseEntity<SurveyPostDto> createSurvey(@RequestBody SurveyCreateDto surveyCreateDto) {
        return ResponseEntity.ok(surveyService.createSurvey(surveyCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (@securityService.isSelf(#id) and hasRole('PSYCHOLOGIST'))")
    public ResponseEntity<SurveyPostDto> updateSurvey(@PathVariable Long id, @RequestBody SurveyUpdateDto surveyUpdateDto) {
        return ResponseEntity.ok(surveyService.updateSurvey(id, surveyUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (@securityService.isSelf(#id) and hasRole('PSYCHOLOGIST'))")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SurveyPostDto>> getSurveyTitleContaining(@RequestParam String title) {
        return ResponseEntity.ok(surveyService.findByTitleContaining(title));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SurveyPostDto>> getSurveyByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(surveyService.findSurveyByUserId(userId));
    }
}
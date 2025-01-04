package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SurveyCreateDto;
import com.yildiz.terapinisec.dto.SurveyPostDto;
import com.yildiz.terapinisec.dto.SurveyUpdateDto;
import com.yildiz.terapinisec.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @GetMapping
    public ResponseEntity<List<SurveyPostDto>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyPostDto> getSurveyById(@PathVariable  Long id) {
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    @PostMapping
    public ResponseEntity<SurveyPostDto> createSurvey(@RequestBody SurveyCreateDto surveyCreateDto) {
        return ResponseEntity.ok(surveyService.createSurvey(surveyCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyPostDto> updateSurvey(@PathVariable Long id, @RequestBody SurveyUpdateDto surveyUpdateDto) {
        return ResponseEntity.ok(surveyService.updateSurvey(id, surveyUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SurveyPostDto>> getSurveyTitleContaining(@RequestParam String title) {
        return ResponseEntity.ok(surveyService.findByTitleContaining(title));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SurveyPostDto>> getSurveyByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(surveyService.findSurveyByUserId(userId));
    }
}
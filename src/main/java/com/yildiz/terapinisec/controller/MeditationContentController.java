package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.MeditationContentCreateDto;
import com.yildiz.terapinisec.dto.MeditationContentResponseDto;
import com.yildiz.terapinisec.dto.MeditationContentUpdateDto;
import com.yildiz.terapinisec.service.MeditationContentService;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/meditationcontent")
public class MeditationContentController {

    @Autowired
    private MeditationContentService meditationContentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MeditationContentResponseDto>> getAllMeditationContent() {
        return ResponseEntity.ok(meditationContentService.getAllMeditationContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MeditationContentResponseDto> getMeditationContentById(@PathVariable Long id) {
        return ResponseEntity.ok(meditationContentService.getMeditationContentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<MeditationContentResponseDto> createMeditationContent(@RequestBody MeditationContentCreateDto meditationContentCreateDto) {
        return ResponseEntity.ok(meditationContentService.createMeditationContent(meditationContentCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<MeditationContentResponseDto> updateMeditationContent(@PathVariable Long id, @RequestBody MeditationContentUpdateDto meditationContentUpdateDto) {
        return ResponseEntity.ok(meditationContentService.updateMeditationContent(id, meditationContentUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<MeditationContentResponseDto> deleteMeditationContent(@PathVariable Long id) {
        meditationContentService.deleteMeditationContentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public-true")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MeditationContentResponseDto>> getPublicMeditationContentByPublicTrue() {
        return ResponseEntity.ok(meditationContentService.findByIsPublicTrue());
    }

    @GetMapping("/public-false")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MeditationContentResponseDto>> getPublicMeditationContentByIsPublicFalse() {
        return ResponseEntity.ok(meditationContentService.findByIsPublicFalse());
    }

    @GetMapping("/type")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MeditationContentResponseDto>> getMeditationContentByType(@RequestParam MeditationSessionType sessionType) {
        return ResponseEntity.ok(meditationContentService.findByType(sessionType));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MeditationContentResponseDto>> getMeditationCreatedById(@PathVariable Long userId) {
        return ResponseEntity.ok(meditationContentService.findByCreatedById(userId));
    }
}
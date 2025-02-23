package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.MeditationSessionCreateDto;
import com.yildiz.terapinisec.dto.MeditationSessionResponseDto;
import com.yildiz.terapinisec.security.SecurityService;
import com.yildiz.terapinisec.service.MeditationSessionService;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/meditationsessions")
public class MeditationSessionController {

    @Autowired
    private MeditationSessionService meditationSessionService;

    @Autowired
    private SecurityService securityService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MeditationSessionResponseDto>> getAllMeditationSessions() {
        return ResponseEntity.ok(meditationSessionService.getAllMeditationSession());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MeditationSessionResponseDto> getMeditationSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(meditationSessionService.getMeditationSessionById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<MeditationSessionResponseDto> createMeditationSession(@RequestBody MeditationSessionCreateDto meditationSessionCreateDto) {
        return ResponseEntity.ok(meditationSessionService.createMeditationSession(meditationSessionCreateDto));
    }

    @GetMapping("/meditator/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<List<MeditationSessionResponseDto>> getSessionsByMeditatorId(@PathVariable Long id) {
        List<MeditationSessionResponseDto> sessions = meditationSessionService.findByMeditatorId(id);
        if (sessions.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(sessions); // 200 OK
    }

    @GetMapping("/count/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#id)")
    public ResponseEntity<Integer> countSessionsByMeditationId(@PathVariable Long id) {
        return ResponseEntity.ok(meditationSessionService.countByMeditationId(id));
    }

    @GetMapping("{id}/content-type")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MeditationSessionResponseDto>> findByMeditatorIdAndMeditationContentType(@PathVariable Long id , @RequestParam MeditationSessionType sessionType) {
        return ResponseEntity.ok(meditationSessionService.findByMeditatorIdAndMeditationContentType(id, sessionType));
    }
}
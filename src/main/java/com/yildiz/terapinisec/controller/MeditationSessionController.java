package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.MeditationSessionCreateDto;
import com.yildiz.terapinisec.dto.MeditationSessionResponseDto;
import com.yildiz.terapinisec.service.MeditationSessionService;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/meditationsessions")
public class MeditationSessionController {

    @Autowired
    private MeditationSessionService meditationSessionService;

    @GetMapping
    public ResponseEntity<List<MeditationSessionResponseDto>> getAllMeditationSessions() {
        return ResponseEntity.ok(meditationSessionService.getAllMeditationSession());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeditationSessionResponseDto> getMeditationSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(meditationSessionService.getMeditationSessionById(id));
    }

    @PostMapping
    public ResponseEntity<MeditationSessionResponseDto> createMeditationSession(@RequestBody MeditationSessionCreateDto meditationSessionCreateDto) {
        return ResponseEntity.ok(meditationSessionService.createMeditationSession(meditationSessionCreateDto));
    }

    @GetMapping("/meditator/{id}")
    public ResponseEntity<List<MeditationSessionResponseDto>> getSessionsByMeditatorId(@PathVariable Long id) {
        List<MeditationSessionResponseDto> sessions = meditationSessionService.findByMeditatorId(id);
        if (sessions.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(sessions); // 200 OK
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> countSessionsByMeditationId(@PathVariable Long id) {
        return ResponseEntity.ok(meditationSessionService.countByMeditationId(id));
    }

    @GetMapping("/meditationtitle")
    public ResponseEntity<List<MeditationSessionResponseDto>> getByMeditationTitleContaining(@RequestParam String keyword) {
        return ResponseEntity.ok(meditationSessionService.findByMeditationTitleContaining(keyword));
    }

    @GetMapping("{id}/content-type")
    public ResponseEntity<List<MeditationSessionResponseDto>> findByMeditatorIdAndMeditationContentType(@PathVariable Long id , @RequestParam MeditationSessionType sessionType) {
        return ResponseEntity.ok(meditationSessionService.findByMeditatorIdAndMeditationContentType(id, sessionType));
    }
}
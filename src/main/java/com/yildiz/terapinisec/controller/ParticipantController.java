package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    private  final ParticipantService participantService;
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ParticipantDto>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantDto participantDto) {
        ParticipantDto createdParticipant = participantService.createParticipant(participantDto);
        return ResponseEntity.ok(createdParticipant);
    }

    @GetMapping("/{sessionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')or @securityService.isSessionParticipant(#sessionId)")
    public ResponseEntity<List<ParticipantDto>> getParticipantBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.ok(participantService.findBySessionId(sessionId));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#userId)")
    public ResponseEntity<List<ParticipantDto>> getParticipantByJoinedUser(@PathVariable Long userId) {
        return ResponseEntity.ok(participantService.findByJoinedUserId(userId));
    }

    @GetMapping("/joinedUser/{sessionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSessionParticipant(#sessionId)")
    public ResponseEntity<List<User>> findJoinedUserBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.ok(participantService.findJoinedUserBySessionId(sessionId));
    }

    @GetMapping("/count/{sessionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSessionParticipant(#sessionId)")
    public ResponseEntity<Long> countBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.ok(participantService.countBySessionId(sessionId));
    }
}

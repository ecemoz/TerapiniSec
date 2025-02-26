package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.SessionCreateDto;
import com.yildiz.terapinisec.dto.SessionDetailedDto;
import com.yildiz.terapinisec.dto.SessionResponseDto;
import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.service.SessionService;
import com.yildiz.terapinisec.util.SessionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SessionResponseDto>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSessionParticipant(#id)")
    public ResponseEntity<SessionDetailedDto> getSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<SessionResponseDto> createSession(@RequestBody SessionCreateDto sessionCreateDto) {
        return ResponseEntity.ok(sessionService.createSession(sessionCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') and @securityService.isSessionOwner(#id)")
    public ResponseEntity<SessionResponseDto> updateSession(@PathVariable Long id, @RequestBody SessionCreateDto sessionCreateDto) {
        return ResponseEntity.ok(sessionService.updateSession(id, sessionCreateDto));
    }

    @PutMapping("/{sessionId}/start")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') and @securityService.isSessionOwner(#sessionId)")
    public ResponseEntity<SessionResponseDto> startSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.startSession(sessionId));
    }

    @PutMapping("/{sessionId}/complete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') and @securityService.isSessionOwner(#sessionId)")
    public ResponseEntity<SessionResponseDto> completeSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.completeSession(sessionId));
    }

    @PutMapping("/{sessionId}/cancel")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') and @securityService.isSessionOwner(#sessionId)")
    public ResponseEntity<SessionResponseDto> cancelSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.cancelSession(sessionId));
    }

    @DeleteMapping("/{sessionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') and @securityService.isSessionOwner(#sessionId)")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessiontype")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SessionResponseDto>> getSessionBySessionTypes(@RequestParam SessionType sessionTypes) {
        return ResponseEntity.ok(sessionService.findBySessionType(sessionTypes));
    }

    @GetMapping("/keyword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SessionResponseDto>> getBySessionNameContainingIgnoreCase(@RequestParam String keyword) {
        return ResponseEntity.ok(sessionService.findBySessionNameContainingIgnoreCase(keyword));
    }

    @GetMapping("/date")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SessionResponseDto>> getBySessionDate(@RequestParam LocalDateTime date) {
        return ResponseEntity.ok(sessionService.getSessionByDate(date));
    }

   @PostMapping("/{sessionId}/participants")
   @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<Void> addParticipant(@PathVariable Long sessionId, @RequestBody Participant participant) {
        sessionService.addParticipant(sessionId, participant);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

   @GetMapping("/{sessionId}/allparticipants")
   @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
   public ResponseEntity<SessionDetailedDto> getSessionWithParticipants(@PathVariable Long sessionId) {
        return ResponseEntity.ok(sessionService.getSessionWithParticipants(sessionId));
   }
}
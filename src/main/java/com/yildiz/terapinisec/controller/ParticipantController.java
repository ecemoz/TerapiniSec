package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.ParticipantDto;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public ResponseEntity<List<ParticipantDto>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> getParticipantById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantDto participantDto) {
        return ResponseEntity.ok(participantService.createParticipant(participantDto));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<List<ParticipantDto>> getParticipantBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.ok(participantService.findBySessionId(sessionId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ParticipantDto>> getParticipantByJoinedUser(@PathVariable Long userId) {
        return ResponseEntity.ok(participantService.findByJoinedUserId(userId));
    }

    @GetMapping("/joinedUser/{sessionId}")
    public ResponseEntity<List<User>> findJoinedUserBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.ok(participantService.findJoinedUserBySessionId(sessionId));
    }

    @GetMapping("/count/{sessionId}")
    public ResponseEntity<Long> countBySessionId(@PathVariable Long sessionId) {
        return ResponseEntity.ok(participantService.countBySessionId(sessionId));
    }
}

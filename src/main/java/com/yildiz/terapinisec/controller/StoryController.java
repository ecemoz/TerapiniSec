package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.StoryCreateDto;
import com.yildiz.terapinisec.dto.StoryDetailedResponseDto;
import com.yildiz.terapinisec.dto.StoryResponseDto;
import com.yildiz.terapinisec.dto.StoryUpdateDto;
import com.yildiz.terapinisec.service.StoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<StoryResponseDto>> getAllStory() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StoryDetailedResponseDto> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST')")
    public ResponseEntity<StoryResponseDto> createStory(@RequestBody StoryCreateDto storyCreateDto) {
        return ResponseEntity.ok(storyService.createStory(storyCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isStoryOwner(#id)")
    public ResponseEntity<StoryResponseDto> updateStory(@PathVariable Long id, @RequestBody StoryUpdateDto storyUpdateDto) {
        return ResponseEntity.ok(storyService.updateStory(id, storyUpdateDto));
    }

    @PostMapping("/deactive-old")
    public ResponseEntity<String> deactivateOldStories() {
        try {
            storyService.deactivateOldStories();
            return ResponseEntity.ok("Deactivated old stories");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured while deactivating old stories :  " +e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isStoryOwner(#id)")
    ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StoryResponseDto> getActiveStory() {
        return ResponseEntity.ok(storyService.findActiveStory());
    }
}
package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.StoryCreateDto;
import com.yildiz.terapinisec.dto.StoryDetailedResponseDto;
import com.yildiz.terapinisec.dto.StoryResponseDto;
import com.yildiz.terapinisec.dto.StoryUpdateDto;
import com.yildiz.terapinisec.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryResponseDto>> getAllStory() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDetailedResponseDto> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryById(id));
    }

    @PostMapping
    public ResponseEntity<StoryResponseDto> createStory(@PathVariable StoryCreateDto storyCreateDto) {
        return ResponseEntity.ok(storyService.createStory(storyCreateDto));
    }

    @PutMapping("/{id}")
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
    ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<StoryResponseDto> getActiveStory() {
        return ResponseEntity.ok(storyService.findActiveStory());
    }
}

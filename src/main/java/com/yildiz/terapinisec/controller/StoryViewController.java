package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.StoryViewCreateDto;
import com.yildiz.terapinisec.dto.StoryViewDetailedDto;
import com.yildiz.terapinisec.dto.StoryViewResponseDto;
import com.yildiz.terapinisec.security.SecurityService;
import com.yildiz.terapinisec.service.StoryViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/storyviews")
public class StoryViewController {

    @Autowired
    private StoryViewService storyViewService;

    @Autowired
    private SecurityService securityService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StoryViewResponseDto> createStoryView (@PathVariable StoryViewCreateDto storyViewCreateDto) {
        return ResponseEntity.ok(storyViewService.createStoryView(storyViewCreateDto));
    }

    @GetMapping("/{userId}/{storyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> hasUserViewedStory (@PathVariable Long storyId , @PathVariable Long userId) {
        return ResponseEntity.ok(storyViewService.hasUserViewedStory(storyId ,userId));
    }

    @PostMapping("/{storyId}/view/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<StoryViewResponseDto> addStoryView (@PathVariable Long storyId, @PathVariable Long userId) {
        try {
            StoryViewResponseDto storyViewResponseDto = storyViewService.addStoryView(storyId, userId);
            return ResponseEntity.ok(storyViewResponseDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/ViewCount")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<Long> getViewCount(@RequestParam Long storyId) {
        return ResponseEntity.ok(storyViewService.getViewCountForStory(storyId));
    }

    @GetMapping("/view/{storyId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<Page<StoryViewResponseDto>> getViewsForStory (@PathVariable Long storyId, @RequestParam Pageable pageable) {
        return ResponseEntity.ok(storyViewService.getViewsForStory(storyId,pageable));
    }

    @GetMapping("/view-user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<Page<StoryViewResponseDto>> getViewsForUser (@PathVariable Long userId, @RequestParam Pageable pageable) {
        return ResponseEntity.ok(storyViewService.getViewsForUser(userId,pageable));
    }

    @GetMapping("/byuserviews/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isSelf(#userId)")
    public ResponseEntity<List<StoryViewDetailedDto>> getViewsByUserWithStories( @PathVariable Long userId) {
        return ResponseEntity.ok(storyViewService.getViewsByUserWithStories(userId));
    }
}
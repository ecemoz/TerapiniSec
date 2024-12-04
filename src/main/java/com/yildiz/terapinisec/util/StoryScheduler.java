package com.yildiz.terapinisec.util;

import com.yildiz.terapinisec.service.StoryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StoryScheduler {

    private final StoryService storyService;

    public StoryScheduler(StoryService storyService) {
        this.storyService = storyService;
    }

    @Scheduled(fixedRate=3600000)
    public void updateStoryStatus() {
        storyService.deactivateOldStories();
    }
}
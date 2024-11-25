package com.yildiz.terapinisec.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class StoryScheduler {

    private final StoryRepository storyRepository;

    public StoryScheduler(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Scheduled(fixedRate=3600000)
    public void updateStoryStatus() {
        LocalDateTime now = LocalDateTime.now();
        storyRepository.findAll().forEach(story -> {
            if (story.isActive() && story.getCreatedAt().isBefore(now.minusHours(24))) {
                story.setActive(false);
                storyRepository.save(story);
            }
        });
    }

    @Configuration
    @EnableScheduling
    public class SchedulerConfig{
    }
}
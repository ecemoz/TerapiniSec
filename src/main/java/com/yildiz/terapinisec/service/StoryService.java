package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Story;
import com.yildiz.terapinisec.repository.StoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public List<Story>getAllStories(){
        return storyRepository.findAll();
    }

    public Optional<Story> getStoryById(Long id){
        return storyRepository.findById(id);
    }

    public Story createStory(Story story){
        return storyRepository.save(story);
    }

    public Story updateStory(Long id, Story updatedStory){
        return storyRepository.findById(id)
                .map(story -> {
                   story.setDescription(updatedStory.getDescription());
                   return storyRepository.save(story);

                })
                .orElseThrow(()-> new RuntimeException("Story not found"));
    }

    @Transactional
    public void deactivateOldStories() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        storyRepository.findAll().forEach(story -> {
            if (story.isActive() && story.getStoryCreatedAt().isBefore(cutoffTime)) {
                story.setActive(false);
                storyRepository.save(story);
            }
        });
    }

    public void deleteStory(Long id) {
        if (storyRepository.existsById(id)) {
            storyRepository.deleteById(id);
        }else {
            throw new RuntimeException("Story not found");
        }
    }

    public Story findByIsActiveTrue(){
        return storyRepository.findByIsActiveTrue();
    }
}
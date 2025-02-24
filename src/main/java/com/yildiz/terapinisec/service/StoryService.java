package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.StoryCreateDto;
import com.yildiz.terapinisec.dto.StoryDetailedResponseDto;
import com.yildiz.terapinisec.dto.StoryResponseDto;
import com.yildiz.terapinisec.dto.StoryUpdateDto;
import com.yildiz.terapinisec.mapper.StoryMapper;
import com.yildiz.terapinisec.model.Story;
import com.yildiz.terapinisec.repository.StoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    public StoryService(StoryRepository storyRepository, StoryMapper storyMapper) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
    }

    public List<StoryResponseDto> getAllStories() {
        List<Story> stories = storyRepository.findAll();
        return storyMapper.toStoryResponseDtoList(stories);
    }

    public StoryDetailedResponseDto getStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        return storyMapper.toStoryDetailedResponseDto(story);
    }

    public StoryResponseDto createStory(StoryCreateDto storyCreateDto) {
        Story story = storyMapper.toStory(storyCreateDto);
        Story savedStory = storyRepository.save(story);
        return storyMapper.toStoryResponseDto(savedStory);
    }

    public StoryResponseDto updateStory(Long id, StoryUpdateDto storyUpdateDto) {
        Story existingStory = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        storyMapper.updateStoryFromDto(storyUpdateDto, existingStory);
        Story updatedStory = storyRepository.save(existingStory);
        return storyMapper.toStoryResponseDto(updatedStory);
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
        } else {
            throw new RuntimeException("Story not found");
        }
    }

    public StoryResponseDto findActiveStory() {
        Story story = storyRepository.findByIsActiveTrue();
        if (story == null) {
            throw new RuntimeException("No active story found");
        }
        return storyMapper.toStoryResponseDto(story);
    }

    public boolean isUserOwnerOfStory(Long userId, Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        return story.getOwner().getId().equals(userId);
    }
}
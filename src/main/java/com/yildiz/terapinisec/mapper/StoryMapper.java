package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.model.Story;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoryMapper {

    public Story toStory(StoryCreateDto createDto) {
        if (createDto == null) {
            return null;
        }

        return Story.builder()
                .storyUrl(createDto.getStoryUrl())
                .description(createDto.getDescription())
                .isActive(createDto.isActive())
                .build();
    }

    public StoryResponseDto toStoryResponseDto(Story story) {
        if (story == null) {
            return null;
        }

        return StoryResponseDto.builder()
                .id(story.getId())
                .storyUrl(story.getStoryUrl())
                .description(story.getDescription())
                .storyCreatedAt(story.getStoryCreatedAt())
                .isActive(story.isActive())
                .viewCount(story.getViewCount())
                .build();
    }

    public StoryDetailedResponseDto toStoryDetailedResponseDto(Story story) {
        if (story == null) {
            return null;
        }

        return StoryDetailedResponseDto.builder()
                .id(story.getId())
                .storyUrl(story.getStoryUrl())
                .description(story.getDescription())
                .storyCreatedAt(story.getStoryCreatedAt())
                .isActive(story.isActive())
                .viewCount(story.getViewCount())
                .storyViews(
                        story.getStoryViews() != null
                                ? story.getStoryViews().stream()
                                .map(storyView -> StoryViewResponseDto.builder()
                                        .id(storyView.getId())
                                        .userId(storyView.getUser() != null ? storyView.getUser().getId() : null)
                                        .viewedAt(storyView.getViewedAt())
                                        .build())
                                .collect(Collectors.toList())
                                : null
                )
                .build();
    }


    public void updateStoryFromDto(StoryUpdateDto updateDto, Story story) {
        if (updateDto == null || story == null) {
            return;
        }

        story.setStoryUrl(updateDto.getStoryUrl());
        story.setDescription(updateDto.getDescription());
        story.setActive(updateDto.isActive());
    }

    public List<StoryResponseDto> toStoryResponseDtoList(List<Story> stories) {
        if (stories == null || stories.isEmpty()) {
            return List.of();
        }

        return stories.stream()
                .map(this::toStoryResponseDto)
                .collect(Collectors.toList());
    }

    public List<StoryDetailedResponseDto> toStoryDetailedResponseDtoList(List<Story> stories) {
        if (stories == null || stories.isEmpty()) {
            return List.of();
        }

        return stories.stream()
                .map(this::toStoryDetailedResponseDto)
                .collect(Collectors.toList());
    }
}

package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.StoryViewCreateDto;
import com.yildiz.terapinisec.dto.StoryViewDetailedDto;
import com.yildiz.terapinisec.dto.StoryViewResponseDto;
import com.yildiz.terapinisec.model.Story;
import com.yildiz.terapinisec.model.StoryView;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoryViewMapper {

    public StoryView toStoryView(StoryViewCreateDto createDto, Story story, User user) {
        if (createDto == null || story == null || user == null) {
            return null;
        }

        return StoryView.builder()
                .story(story)
                .user(user)
                .build();
    }

    public StoryViewResponseDto toStoryViewResponseDto(StoryView storyView) {
        if (storyView == null) {
            return null;
        }

        return StoryViewResponseDto.builder()
                .id(storyView.getId())
                .viewedAt(storyView.getViewedAt())
                .storyId(storyView.getStory() != null ? storyView.getStory().getId() : null)
                .userId(storyView.getUser() != null ? storyView.getUser().getId() : null)
                .build();
    }

    public StoryViewDetailedDto toStoryViewDetailedDto(StoryView storyView) {
        if (storyView == null) {
            return null;
        }

        return StoryViewDetailedDto.builder()
                .id(storyView.getId())
                .viewedAt(storyView.getViewedAt())
                .storyDescription(storyView.getStory() != null ? storyView.getStory().getDescription() : null)
                .username(storyView.getUser() != null ? storyView.getUser().getUserName() : null)
                .build();
    }

    public List<StoryViewResponseDto> toStoryViewResponseDtoList(List<StoryView> storyViews) {
        if (storyViews == null || storyViews.isEmpty()) {
            return List.of();
        }

        return storyViews.stream()
                .map(this::toStoryViewResponseDto)
                .collect(Collectors.toList());
    }

    public List<StoryViewDetailedDto> toStoryViewDetailedDtoList(List<StoryView> storyViews) {
        if (storyViews == null || storyViews.isEmpty()) {
            return List.of();
        }

        return storyViews.stream()
                .map(this::toStoryViewDetailedDto)
                .collect(Collectors.toList());
    }
}

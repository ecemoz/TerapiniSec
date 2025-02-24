package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.StoryViewCreateDto;
import com.yildiz.terapinisec.dto.StoryViewDetailedDto;
import com.yildiz.terapinisec.dto.StoryViewResponseDto;
import com.yildiz.terapinisec.mapper.StoryViewMapper;
import com.yildiz.terapinisec.model.Story;
import com.yildiz.terapinisec.model.StoryView;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.StoryRepository;
import com.yildiz.terapinisec.repository.StoryViewRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoryViewService {

    private final StoryViewRepository storyViewRepository;
    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final StoryViewMapper storyViewMapper;

    public StoryViewService(StoryViewRepository storyViewRepository,
                            StoryRepository storyRepository,
                            UserRepository userRepository,
                            StoryViewMapper storyViewMapper) {
        this.storyViewRepository = storyViewRepository;
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.storyViewMapper = storyViewMapper;
    }

    public StoryViewResponseDto createStoryView(StoryViewCreateDto storyViewCreateDto) {

        Story story = storyRepository.findById(storyViewCreateDto.getStoryId())
                .orElseThrow(() -> new RuntimeException("Story Not Found"));

        User user = userRepository.findById(storyViewCreateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (storyViewRepository.existsByStoryIdAndUserId(story.getId(), user.getId())) {
            throw new RuntimeException("User has already viewed this story.");
        }

        StoryView storyView = storyViewMapper.toStoryView(storyViewCreateDto, story, user);
        StoryView savedStoryView = storyViewRepository.save(storyView);

        return storyViewMapper.toStoryViewResponseDto(savedStoryView);
    }


    public boolean hasUserViewedStory(Long storyId,Long userId) {
        return storyViewRepository.existsByStoryIdAndUserId(storyId,userId);
    }

    @Transactional
    public StoryViewResponseDto addStoryView (Long storyId,Long userId) {
        if (storyViewRepository.existsByStoryIdAndUserId(storyId,userId)) {
            throw new RuntimeException("User has already  viewed story");
        }

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StoryView storyView = new StoryView();
        storyView.setStory(story);
        storyView.setUser(user);

        StoryView savedStoryView = storyViewRepository.save(storyView);
        return storyViewMapper.toStoryViewResponseDto(savedStoryView);
    }

    public long getViewCountForStory(Long storyId) {
        return storyViewRepository.countByStoryId(storyId);
    }

    public Page<StoryViewResponseDto> getViewsForStory(Long storyId, Pageable pageable) {
        Page<StoryView> storyViews = storyViewRepository.findByStoryId(storyId, pageable);
        return storyViews.map(storyViewMapper::toStoryViewResponseDto);
    }

    public Page<StoryViewResponseDto>getViewsForUser(Long userId, Pageable pageable) {
       Page<StoryView> storyViews = storyViewRepository.findByUserId(userId, pageable);
       return storyViews.map(storyViewMapper::toStoryViewResponseDto);
    }

    public List<StoryViewDetailedDto>getViewsByUserWithStories(Long userId) {
        List<StoryView> storyViews = storyViewRepository.findViewsByUserWithStories(userId);
        return storyViewMapper.toStoryViewDetailedDtoList(storyViews);
    }
}
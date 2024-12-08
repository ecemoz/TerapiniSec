package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Story;
import com.yildiz.terapinisec.model.StoryView;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.StoryRepository;
import com.yildiz.terapinisec.repository.StoryViewRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoryViewService {

    @Autowired
    private StoryViewRepository storyViewRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;



    public StoryView createStoryView(StoryView storyView) {
        return storyViewRepository.save(storyView);
    }

    public boolean hasUserViewedStory(Long storyId,Long userId) {
        return storyViewRepository.existsByStoryIdAndUserId(storyId,userId);
    }

    @Transactional
    public StoryView addStoryView (Long storyId,Long userId) {
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

        return storyViewRepository.save(storyView);
    }

    public long getViewCountForStory(Long storyId) {
        return storyViewRepository.countByStoryId(storyId);
    }

    public Page<StoryView> getViewsForStory(Long storyId, Pageable pageable) {
        return storyViewRepository.findByStoryId(storyId,pageable);
    }

    public Page<StoryView>getViewsForUser(Long userId, Pageable pageable) {
        return storyViewRepository.findByUserId(userId,pageable);
    }

    public List<StoryView>getViewsByUserWithStories(Long userId) {
        return storyViewRepository.findViewsByUserWithStories(userId);
    }
}
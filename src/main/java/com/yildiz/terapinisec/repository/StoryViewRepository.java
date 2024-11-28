package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.StoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView, Long> {

    StoryView findByStoryId(Long storyId);
    StoryView countByStoryId(Long storyId);
    StoryView existsByUserIdAndStoryId(Long userId, Long storyId);
}
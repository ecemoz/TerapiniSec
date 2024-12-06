package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.StoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView, Long> {

    List<StoryView> findByStoryId(Long storyId);
    List<StoryView> findByUserId(Long userId);
    long countByStoryId(Long storyId);
}
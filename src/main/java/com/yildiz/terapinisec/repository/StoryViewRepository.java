package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.StoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView, Long> {

    Page<StoryView> findByStoryId(Long storyId, Pageable pageable);
    Page<StoryView> findByUserId(Long userId,Pageable pageable);
    long countByStoryId(Long storyId);
    boolean existsByStoryIdAndUserId(Long storyId, Long userId);

    @Query("SELECT sv FROM StoryView sv JOIN FETCH sv.story WHERE sv.user.id = :userId")
    List<StoryView>findViewsByUserWithStories(@Param("userId") Long userId);
}
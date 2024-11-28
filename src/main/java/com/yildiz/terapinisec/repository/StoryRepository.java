package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    Story findByIsActiveTrue();
}

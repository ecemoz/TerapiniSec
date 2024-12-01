package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal>findByGoalType(String goalType);
    List<Goal>findByIsCompletedTrue();
    List<Goal>findByIsCompletedFalse();
    List<Goal>findByGoalOwnerId(Long userId);
    List<Goal>findByGoalOwnerIdAndIsCompletedFalse(Long userId);
}
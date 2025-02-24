package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Goal;
import com.yildiz.terapinisec.util.GoalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal>findByGoalType(GoalType goalType);
    List<Goal>findByGoalCompleteTrue();
    List<Goal>findByGoalCompleteFalse();
    List<Goal>findByGoalOwnerId(Long userId);
    List<Goal>findByGoalOwnerIdAndGoalCompleteFalse(Long userId);

    @Query("SELECT COUNT(g) FROM Goal g WHERE g.goalOwner.id = :userId AND g.goalComplete = true AND g.goalEndDate BETWEEN :startDate AND :endDate ")
    long countCompletedGoalsByUserAndDateRange(@Param("userId") Long userId, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

    @Query("SELECT COUNT(g) FROM Goal g WHERE g.goalOwner.id = :userId AND g.goalEndDate BETWEEN :startDate AND :endDate ")
    long countGoalsByUserAndDateRange (@Param("userId") Long userId, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
    boolean existsByIdAndGoalOwnerId(Long goalId, Long goalOwnerId);
}
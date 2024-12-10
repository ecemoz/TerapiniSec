package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Goal;
import com.yildiz.terapinisec.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public Goal createGoal(Goal goal) {
        goal.setGoalComplete(goal.isGoalComplete());
        goal.setGoalDescription(goal.getGoalDescription());
        goal.setGoalStartDate(goal.getGoalStartDate());
        goal.setGoalEndDate(goal.getGoalEndDate());
        goal.setGoalType(goal.getGoalType());
        goal.setGoalOwner(goal.getGoalOwner());
        return goalRepository.save(goal);
    }

    public Goal updateGoal(Long id , Goal updatedGoal) {
        return goalRepository.findById(id)
                .map(goal -> {
                    goal.setGoalDescription(updatedGoal.getGoalDescription());
                    goal.setGoalStartDate(updatedGoal.getGoalStartDate());
                    goal.setGoalEndDate(updatedGoal.getGoalEndDate());
                    goal.setGoalType(updatedGoal.getGoalType());
                    goal.setGoalComplete(updatedGoal.isGoalComplete());
                    return goalRepository.save(goal);
                })
                .orElseThrow(()-> new RuntimeException(" Goal not found"));
    }

    public void deleteGoal(Long id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
        } else {
            throw new RuntimeException(" Goal not found");
        }
    }

    public List<Goal>findByGoalType(String goalType) {
        return goalRepository.findByGoalType(goalType);
    }

    public List<Goal>findByIsCompletedTrue() {
        return goalRepository.findByIsCompletedTrue();
    }

    public List<Goal>findByIsCompletedFalse() {
        return goalRepository.findByIsCompletedFalse();
    }

    public List<Goal>findByGoalOwnerId(Long userId) {
        return goalRepository.findByGoalOwnerId(userId);
    }

    public List<Goal>findByGoalOwnerIdAndIsCompletedFalse(Long userId) {
        return goalRepository.findByGoalOwnerIdAndIsCompletedFalse(userId);
    }
}
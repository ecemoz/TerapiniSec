package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.GoalCreateDto;
import com.yildiz.terapinisec.dto.GoalResponseDto;
import com.yildiz.terapinisec.dto.GoalUpdateDto;
import com.yildiz.terapinisec.mapper.GoalMapper;
import com.yildiz.terapinisec.model.Goal;
import com.yildiz.terapinisec.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalMapper goalMapper;

    public List<GoalResponseDto> getAllGoals() {
        List<Goal> goals = goalRepository.findAll();
        return goals.stream()
                .map(goalMapper::toGoalResponseDto)
                .collect(Collectors.toList());
    }

    public GoalResponseDto getGoalById(Long id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));
        return goalMapper.toGoalResponseDto(goal);
    }

    public GoalResponseDto createGoal(GoalCreateDto goalCreateDto) {
        Goal goal = goalMapper.toGoal(goalCreateDto);
        Goal savedGoal = goalRepository.save(goal);
        return goalMapper.toGoalResponseDto(savedGoal);
    }

    public GoalResponseDto updateGoal(Long id , GoalUpdateDto goalUpdateDto) {
        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        goalMapper.updateGoalFromDto(goalUpdateDto, existingGoal);
        Goal updatedGoal = goalRepository.save(existingGoal);
        return goalMapper.toGoalResponseDto(updatedGoal);
    }

    public void deleteGoal(Long id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
        } else {
            throw new RuntimeException(" Goal not found");
        }
    }

    public List<GoalResponseDto>findByGoalType(String goalType) {
       List<Goal> goals = goalRepository.findByGoalType(goalType);
        return goals.stream()
                .map(goalMapper::toGoalResponseDto)
                .collect(Collectors.toList());
    }

    public List<GoalResponseDto>findByIsCompletedTrue() {
        List<Goal> goals = goalRepository.findByIsCompletedTrue();
        return goals.stream()
                .map(goalMapper::toGoalResponseDto)
                .collect(Collectors.toList());
    }

    public List<GoalResponseDto>findByIsCompletedFalse() {
       List<Goal> goals = goalRepository.findByIsCompletedFalse();
        return goals.stream()
                .map(goalMapper::toGoalResponseDto)
                .collect(Collectors.toList());
    }

    public List<GoalResponseDto>findByGoalOwnerId(Long userId) {
        List<Goal> goals = goalRepository.findByGoalOwnerId(userId);
        return goals.stream()
                .map(goalMapper::toGoalResponseDto)
                .collect(Collectors.toList());
    }

    public List<GoalResponseDto>findByGoalOwnerIdAndIsCompletedFalse(Long userId) {
        List<Goal> goals = goalRepository.findByGoalOwnerIdAndIsCompletedFalse(userId);
        return goals.stream()
                .map(goalMapper::toGoalResponseDto)
                .collect(Collectors.toList());
    }
}
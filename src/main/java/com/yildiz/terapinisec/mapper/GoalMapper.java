package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.GoalCreateDto;
import com.yildiz.terapinisec.dto.GoalResponseDto;
import com.yildiz.terapinisec.dto.GoalUpdateDto;
import com.yildiz.terapinisec.model.Goal;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoalMapper {

    public Goal toGoal(GoalCreateDto createDto, User goalOwner) {
        if (createDto == null || goalOwner == null) {
            return null;
        }

        return Goal.builder()
                .goalType(createDto.getGoalType())
                .goalDescription(createDto.getGoalDescription())
                .goalEndDate(createDto.getGoalEndDate())
                .goalOwner(goalOwner)
                .build();
    }

    public GoalResponseDto toGoalResponseDto(Goal goal) {
        if (goal == null) {
            return null;
        }

        return GoalResponseDto.builder()
                .id(goal.getId())
                .goalType(goal.getGoalType())
                .goalDescription(goal.getGoalDescription())
                .goalStartDate(goal.getGoalStartDate())
                .goalEndDate(goal.getGoalEndDate())
                .goalComplete(goal.isGoalComplete())
                .goalOwnerUsername(goal.getGoalOwner() != null ? goal.getGoalOwner().getUserName() : null)
                .build();
    }

    public void updateGoalFromDto(GoalUpdateDto updateDto, Goal goal) {
        if (updateDto == null || goal == null) {
            return;
        }

        goal.setGoalType(updateDto.getGoalType());
        goal.setGoalDescription(updateDto.getGoalDescription());
        goal.setGoalEndDate(updateDto.getGoalEndDate());
        goal.setGoalComplete(updateDto.isGoalComplete());
    }

    public List<GoalResponseDto> toGoalResponseDtoList(List<Goal> goals) {
        if (goals == null || goals.isEmpty()) {
            return List.of();
        }

        return goals.stream()
                .map(this::toGoalResponseDto)
                .collect(Collectors.toList());
    }
}

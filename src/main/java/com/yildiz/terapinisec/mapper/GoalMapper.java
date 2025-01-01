package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.GoalCreateDto;
import com.yildiz.terapinisec.dto.GoalResponseDto;
import com.yildiz.terapinisec.dto.GoalUpdateDto;
import com.yildiz.terapinisec.model.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    @Mapping(source = "goalOwnerId" , target = "goalOwner.id")
    Goal toGoal (GoalCreateDto goalCreateDto);

    @Mapping(source = "goalOwner.username" , target = "goalOwnerUsername")
    GoalResponseDto toGoalResponseDto(Goal goal);

    void updateGoalFromDto(GoalUpdateDto goalUpdateDto , @MappingTarget Goal goal);
}
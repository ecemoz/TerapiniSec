package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.TaskCreateDto;
import com.yildiz.terapinisec.dto.TaskResponseDto;
import com.yildiz.terapinisec.dto.TaskUpdateDto;
import com.yildiz.terapinisec.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "assignees.id", source = "assigneeId")
    Task toTask(TaskCreateDto taskCreateDto);

    @Mapping (target = "assigneeId" , source = "assignees.id")
    TaskResponseDto toTaskResponseDto(Task task);

    void updateTaskFromDto(TaskUpdateDto taskUpdateDto, @MappingTarget Task task );

    List<TaskResponseDto> taskResponseDtoList(List<Task> tasks);
}
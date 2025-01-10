package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.TaskCreateDto;
import com.yildiz.terapinisec.dto.TaskResponseDto;
import com.yildiz.terapinisec.dto.TaskUpdateDto;
import com.yildiz.terapinisec.model.Task;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toTask(TaskCreateDto taskCreateDto, User assignee) {
        if (taskCreateDto == null || assignee == null) {
            return null;
        }

        return Task.builder()
                .taskName(taskCreateDto.getTaskName())
                .taskDescription(taskCreateDto.getTaskDescription())
                .dueDate(taskCreateDto.getDueDate())
                .isCompleted(false) // Yeni oluşturulan görevler tamamlanmamış olarak başlar
                .assignees(assignee)
                .build();
    }

    public TaskResponseDto toTaskResponseDto(Task task) {
        if (task == null) {
            return null;
        }

        return TaskResponseDto.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskDescription(task.getTaskDescription())
                .dueDate(task.getDueDate())
                .isCompleted(task.isCompleted())
                .assigneeId(task.getAssignees() != null ? task.getAssignees().getId() : null)
                .build();
    }

    public void updateTaskFromDto(TaskUpdateDto taskUpdateDto, Task task) {
        if (taskUpdateDto == null || task == null) {
            return;
        }

        task.setTaskName(taskUpdateDto.getTaskName());
        task.setTaskDescription(taskUpdateDto.getTaskDescription());
        task.setDueDate(taskUpdateDto.getDueDate());
        task.setCompleted(taskUpdateDto.isCompleted());
    }
}

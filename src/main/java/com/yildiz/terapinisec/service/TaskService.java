package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.TaskCreateDto;
import com.yildiz.terapinisec.dto.TaskResponseDto;
import com.yildiz.terapinisec.dto.TaskUpdateDto;
import com.yildiz.terapinisec.mapper.TaskMapper;
import com.yildiz.terapinisec.model.Task;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.TaskRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto createTask(TaskCreateDto taskCreateDto) {
        User assignee = userRepository.findById(taskCreateDto.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskMapper.toTask(taskCreateDto, assignee);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDto(savedTask);
    }

    public TaskResponseDto updateTask(Long id, TaskUpdateDto taskUpdateDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskMapper.updateTaskFromDto(taskUpdateDto, existingTask);
        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toTaskResponseDto(updatedTask);
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public TaskResponseDto findByTaskName(String taskName) {
        Task task = taskRepository.findByTaskName(taskName);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        return taskMapper.toTaskResponseDto(task);
    }

    public List<TaskResponseDto> findByDueDateBefore(LocalDateTime dueDate) {
        List<Task> tasks = taskRepository.findByDueDateBefore(dueDate);
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findByIsCompletedTrue() {
        List<Task> tasks = taskRepository.findByIsCompletedTrue();
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findByIsCompletedFalse() {
        List<Task> tasks = taskRepository.findByIsCompletedFalse();
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByAssignees_Id(userId);
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findByUserIdAndIsCompletedFalse(Long userId) {
        List<Task> tasks = taskRepository.findByAssignees_IdAndIsCompletedFalse(userId);
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findByUserIdDueDateBefore(Long userId, LocalDateTime dueDate) {
        List<Task> tasks = taskRepository.findByAssignees_IdAndDueDateBefore(userId, dueDate);
        return tasks.stream()
                .map(taskMapper::toTaskResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTaskCompletion(Long taskId, boolean isCompleted) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(isCompleted);
        taskRepository.save(task);
    }
}
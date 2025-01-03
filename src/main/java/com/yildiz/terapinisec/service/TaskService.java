package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.TaskCreateDto;
import com.yildiz.terapinisec.dto.TaskResponseDto;
import com.yildiz.terapinisec.dto.TaskUpdateDto;
import com.yildiz.terapinisec.mapper.TaskMapper;
import com.yildiz.terapinisec.model.Task;
import com.yildiz.terapinisec.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.taskResponseDtoList(tasks);
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return taskMapper.toTaskResponseDto(task);
    }

    public TaskResponseDto createTask(TaskCreateDto taskcreateDto) {
       Task task = taskMapper.toTask(taskcreateDto);
       Task savedTask = taskRepository.save(task);
       return taskMapper.toTaskResponseDto(savedTask);
    }

    public TaskResponseDto updateTask(Long id, TaskUpdateDto taskupdateDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskMapper.updateTaskFromDto(taskupdateDto, task);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDto(savedTask);
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
        return taskMapper.toTaskResponseDto(task);
    }

    public List <TaskResponseDto>findByDueDateBefore(LocalDateTime dueDate){
        List<Task> tasks = taskRepository.findByDueDateBefore(dueDate);
        return taskMapper.taskResponseDtoList(tasks);
    }

    public List<TaskResponseDto> findByIsCompletedTrue() {
        List<Task> tasks = taskRepository.findByIsCompletedTrue();
        return taskMapper.taskResponseDtoList(tasks);
    }

    public List<TaskResponseDto> findByIsCompletedFalse() {
        List<Task> tasks = taskRepository.findByIsCompletedFalse();
        return taskMapper.taskResponseDtoList(tasks);
    }

    public List<TaskResponseDto> findByUserId(Long userId){
        List<Task> tasks = taskRepository.findByUserId(userId);
        return taskMapper.taskResponseDtoList(tasks);
    }

    public List<TaskResponseDto> findByUserIdAndIsCompletedFalse(Long userId){
        List<Task> tasks = taskRepository.findByUserIdAndIsCompletedFalse(userId);
        return taskMapper.taskResponseDtoList(tasks);

    }

    public List<TaskResponseDto> findByUserIdDueDateBefore(Long userId ,LocalDateTime dueDate){
        List<Task> tasks = taskRepository.findByUserIdDueDateBefore(userId,dueDate);
        return taskMapper.taskResponseDtoList(tasks);
    }

    @Transactional
    public void updateTaskCompletion (Long taskId,boolean isCompleted){
        taskRepository.updateTaskCompletion(taskId,isCompleted);
    }
}
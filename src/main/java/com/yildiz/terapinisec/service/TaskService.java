package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Task;
import com.yildiz.terapinisec.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        task.setTaskName(task.getTaskName());
        task.setTaskDescription(task.getTaskDescription());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id,Task updatedtask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTaskName(updatedtask.getTaskName());
                    task.setTaskDescription(updatedtask.getTaskDescription());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateDueDate(Long id, LocalDateTime newDueDate) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (newDueDate.isBefore(LocalDateTime.now())) {
                        throw new RuntimeException("Due date cannot be before current date");
                    }
                    task.setDueDate(newDueDate);
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public Task findByTaskName(String taskName) {
        return taskRepository.findByTaskName(taskName);
    }

    public List <Task>findByDueDateBefore(LocalDateTime dueDate){
        return taskRepository.findByDueDateBefore(dueDate);
    }

    public List<Task> findByIsCompletedTrue() {
        return taskRepository.findByIsCompletedTrue();
    }

    public List<Task> findByIsCompletedFalse() {
        return taskRepository.findByIsCompletedFalse();
    }

    public List<Task> findByUserId(Long userId){
        return taskRepository.findByUserId(userId);
    }

    public List<Task> findByUserIdAndIsCompletedFalse(Long userId){
        return taskRepository.findByUserIdAndIsCompletedFalse(userId);
    }

    public List<Task> findByUserIdDueDateBefore(Long userId ,LocalDateTime dueDate){
        return taskRepository.findByUserIdDueDateBefore(userId,dueDate);
    }

    @Transactional
    public void updateTaskCompletion (Long taskId,boolean isCompleted){
        taskRepository.updateTaskCompletion(taskId,isCompleted);
    }
}
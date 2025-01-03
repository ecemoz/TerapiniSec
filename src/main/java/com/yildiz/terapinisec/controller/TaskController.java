package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.TaskCreateDto;
import com.yildiz.terapinisec.dto.TaskResponseDto;
import com.yildiz.terapinisec.dto.TaskUpdateDto;
import com.yildiz.terapinisec.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskCreateDto taskCreateDto) {
        return ResponseEntity.ok(taskService.createTask(taskCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateDto taskUpdateDto) {
        return ResponseEntity.ok(taskService.updateTask(id, taskUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task successfully deleted.");
    }

    @GetMapping("/name")
    public ResponseEntity<TaskResponseDto> findByTaskName(@RequestParam String taskName) {
        return ResponseEntity.ok(taskService.findByTaskName(taskName));
    }

    @GetMapping("/due-date/before")
    public ResponseEntity<List<TaskResponseDto>> findByDueDateBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate) {
        return ResponseEntity.ok(taskService.findByDueDateBefore(dueDate));
    }

    @GetMapping("/completed/true")
    public ResponseEntity<List<TaskResponseDto>> findByIsCompletedTrue() {
        return ResponseEntity.ok(taskService.findByIsCompletedTrue());
    }

    @GetMapping("/completed/false")
    public ResponseEntity<List<TaskResponseDto>> findByIsCompletedFalse() {
        return ResponseEntity.ok(taskService.findByIsCompletedFalse());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDto>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    @GetMapping("/user/{userId}/incomplete")
    public ResponseEntity<List<TaskResponseDto>> findByUserIdAndIsCompletedFalse(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByUserIdAndIsCompletedFalse(userId));
    }

    @GetMapping("/user/{userId}/due-date/before")
    public ResponseEntity<List<TaskResponseDto>> findByUserIdDueDateBefore(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate) {
        return ResponseEntity.ok(taskService.findByUserIdDueDateBefore(userId, dueDate));
    }

    @PutMapping("/{id}/completion")
    public ResponseEntity<String> updateTaskCompletion(
            @PathVariable Long id,
            @RequestParam boolean isCompleted) {
        taskService.updateTaskCompletion(id, isCompleted);
        return ResponseEntity.ok("Task completion status updated.");
    }
}
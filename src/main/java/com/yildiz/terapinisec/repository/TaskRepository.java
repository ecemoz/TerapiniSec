package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTaskName(String taskName);
    Task findByDueDateBefore(LocalDateTime dueDate);
    Task findByIsCompletedTrue();
    Task findByIsCompletedFalse();
    Task findByUserId(Long userId);
    Task findByUserIdAndIsCompletedFalse(Long userId);
    Task findByUserIdDueDateBefore(Long userId ,LocalDateTime dueDate);

    @Modifying
    @Query("UPDATE Task t SET t.isCompleted = :isCompleted WHERE t.id = :taskId")
    void updateTaskCompletion(@Param("taskId") Long taskId, @Param("isCompleted") boolean isCompleted);
}
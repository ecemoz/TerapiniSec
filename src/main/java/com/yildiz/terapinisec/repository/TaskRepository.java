package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByTaskName(String taskName);
    List<Task>findByDueDateBefore(LocalDateTime dueDate);
    List<Task> findByIsCompletedTrue();
    List<Task> findByIsCompletedFalse();
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndIsCompletedFalse(Long userId);
    List<Task> findByUserIdDueDateBefore(Long userId ,LocalDateTime dueDate);

    @Modifying
    @Query("UPDATE Task t SET t.isCompleted = :isCompleted WHERE t.id = :taskId")
    void updateTaskCompletion(@Param("taskId") Long taskId, @Param("isCompleted") boolean isCompleted);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignees.id = :userId AND t.isCompleted = true AND t.dueDate BETWEEN :startDate AND : endDate")
    long countCompletedTasksByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignees.id = :userId AND t.dueDate BETWEEN :startDate AND :endDate")
    long countTasksByUserAndDateRange(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);
}
package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskResponseDto {

    private Long id;
    private String taskName;
    private String taskDescription;
    private LocalDateTime dueDate;
    private boolean isCompleted;
    private Long assigneeId;
}

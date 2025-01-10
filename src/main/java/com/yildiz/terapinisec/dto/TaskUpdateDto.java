package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskUpdateDto {

    private String taskName;
    private String taskDescription;
    private LocalDateTime dueDate;
    private boolean isCompleted;
}
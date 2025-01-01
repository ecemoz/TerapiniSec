package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.GoalType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GoalResponseDto {

    private Long id;
    private List<GoalType> goalType;
    private String goalDescription;
    private LocalDateTime goalStartDate;
    private LocalDateTime goalEndDate;
    private boolean goalComplete;
    private String goalOwnerUsername;
}
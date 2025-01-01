package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.GoalType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GoalUpdateDto {

    private List<GoalType> goalType;
    private String goalDescription;
    private LocalDateTime goalEndDate;
    private boolean goalComplete;
}
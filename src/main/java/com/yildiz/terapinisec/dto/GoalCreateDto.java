package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.GoalType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GoalCreateDto {

    private List<GoalType> goalType;
    private String goalDescription;
    private LocalDateTime goalEndTime;
    private Long goalOwnerId;
}
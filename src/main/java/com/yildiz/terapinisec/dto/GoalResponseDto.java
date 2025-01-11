package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.GoalType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GoalResponseDto {

    private Long id;
    private GoalType goalType;
    private String goalDescription;
    private LocalDateTime goalStartDate;
    private LocalDateTime goalEndDate;
    private boolean goalComplete;
    private String goalOwnerUsername;
}
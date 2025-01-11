package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.SleepQuality;
import lombok.Builder;
import lombok.Data;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
public class SleepLogResponseDto {

    private Long id;
    private Duration sleepDuration;
    private int sleepQuality;
    private LocalDateTime sleepDate;
    private String sleeperUsername;
}
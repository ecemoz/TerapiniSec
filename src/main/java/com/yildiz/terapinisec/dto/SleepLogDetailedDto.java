package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.SleepQuality;
import lombok.Builder;
import lombok.Data;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
public class SleepLogDetailedDto {

    private Long id;
    private Duration sleepDuration;
    private SleepQuality sleepQuality;
    private LocalDateTime sleepDate;
    private String sleeperUsername;
    private Long sleeperId;
}
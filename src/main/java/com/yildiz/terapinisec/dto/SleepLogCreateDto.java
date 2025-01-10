package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.SleepQuality;
import lombok.Builder;
import lombok.Data;
import java.time.Duration;

@Data
@Builder
public class SleepLogCreateDto {

    private Duration sleepDuration;
    private SleepQuality sleepQuality;
    private Long userId;
}
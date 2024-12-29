package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.SessionType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SessionCreateDto {

    private String sessionName;
    private LocalDateTime sessionDateTime;
    private SessionType sessionType;
    private int durationMinutes;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.SessionStatus;
import com.yildiz.terapinisec.util.SessionType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class SessionResponseDto {

    private Long id;
    private String sessionName;
    private LocalDateTime sessionDateTime;
    private SessionType sessionType;
    private int durationMinutes;
    private SessionStatus sessionStatus;
}

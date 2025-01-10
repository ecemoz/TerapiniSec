package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.util.SessionType;
import lombok.Builder;
import lombok.Data;
import com.yildiz.terapinisec.util.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SessionDetailedDto {

    private Long id;
    private String sessionName;
    private LocalDateTime sessionDateTime;
    private SessionType sessionType;
    private int durationMinutes;
    private SessionStatus sessionStatus;
    private List<Participant> participants;
}
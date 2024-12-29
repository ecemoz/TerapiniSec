package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.model.Participant;
import com.yildiz.terapinisec.util.SessionType;
import lombok.Data;
import org.springframework.web.bind.support.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SessionDetailedDto {

    private Long id;
    private String sessionName;
    private LocalDateTime sessionDateTime;
    private SessionType sessionType;
    private int durationMinutes;
    private SessionStatus sessionStatus;
    private List<Participant> participants;
}
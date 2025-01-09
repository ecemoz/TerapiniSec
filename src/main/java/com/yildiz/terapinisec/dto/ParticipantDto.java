package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ParticipantDto {

    private Long id ;
    private LocalDateTime joinedAt;
    private String participantName;
    private String participantEmail;
    private Long sessionId;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.AppointmentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {

    private Long id;
    private LocalDateTime appointmentDate;
    private AppointmentStatus appointmentStatus;
    private String appointmentClientUsername;
    private String therapistUsername;
}
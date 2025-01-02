package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.AppointmentStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentCreateDto {

    private LocalDateTime appointmentTime;
    private Long appointmentClientId;
    private Long therapistId;
    private AppointmentStatus appointmentStatus = AppointmentStatus.PENDING;
}
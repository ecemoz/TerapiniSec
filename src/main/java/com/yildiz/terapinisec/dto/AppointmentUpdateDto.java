package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.AppointmentStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentUpdateDto {

    private LocalDateTime appointmentDate;
    private AppointmentStatus appointmentStatus;
}
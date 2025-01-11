package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateDto {

    private LocalDateTime appointmentDate;
    private AppointmentStatus appointmentStatus;

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

}
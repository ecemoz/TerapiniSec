package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.AppointmentCreateDto;
import com.yildiz.terapinisec.dto.AppointmentResponseDto;
import com.yildiz.terapinisec.dto.AppointmentUpdateDto;
import com.yildiz.terapinisec.model.Appointment;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {

    public Appointment toAppointment(AppointmentCreateDto createDto, User client, User therapist) {
        if (createDto == null) {
            return null;
        }

        return Appointment.builder()
                .appointmentDate(createDto.getAppointmentTime())
                .appointmentStatus(createDto.getAppointmentStatus())
                .appointmentClients(client)
                .therapist(therapist)
                .build();
    }

    public AppointmentResponseDto toAppointmentResponseDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentStatus(appointment.getAppointmentStatus())
                .appointmentClientUsername(appointment.getAppointmentClients() != null
                        ? appointment.getAppointmentClients().getUsername() : null)
                .therapistUsername(appointment.getTherapist() != null
                        ? appointment.getTherapist().getUsername() : null)
                .build();
    }

    // Update DTO -> Model dönüşümü
    public void updateAppointmentFromDto(AppointmentUpdateDto updateDto, Appointment appointment) {
        if (updateDto == null || appointment == null) {
            return;
        }

        appointment.setAppointmentDate(updateDto.getAppointmentDate());
        appointment.setAppointmentStatus(updateDto.getAppointmentStatus());
    }

    public List<AppointmentResponseDto> toAppointmentResponseDtoList(List<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            return List.of();
        }

        return appointments.stream()
                .map(this::toAppointmentResponseDto)
                .collect(Collectors.toList());
    }
}
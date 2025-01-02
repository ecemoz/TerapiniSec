package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.AppointmentCreateDto;
import com.yildiz.terapinisec.dto.AppointmentResponseDto;
import com.yildiz.terapinisec.dto.AppointmentUpdateDto;
import com.yildiz.terapinisec.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface AppointmentMapper {

    @Mapping(source = "appointmentClientId" , target = "appointmentClients.id")
    @Mapping(source = "therapistId" , target = "therapist.id")
    Appointment toAppointment(AppointmentCreateDto appointmentCreateDto);

    @Mapping(source = "appointmentClients.username" , target = "appointmentClientUsername")
    @Mapping(source = "therapist.username" , target = "therapistUsername")
    AppointmentResponseDto toAppointmentResponseDto(Appointment appointment);

    void updateAppointmentFromDto(AppointmentUpdateDto appointmentUpdateDto, @MappingTarget Appointment appointment);
}
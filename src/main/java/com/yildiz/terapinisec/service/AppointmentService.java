package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.AppointmentCreateDto;
import com.yildiz.terapinisec.dto.AppointmentResponseDto;
import com.yildiz.terapinisec.dto.AppointmentUpdateDto;
import com.yildiz.terapinisec.mapper.AppointmentMapper;
import com.yildiz.terapinisec.model.Appointment;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.AppointmentRepository;
import com.yildiz.terapinisec.util.AppointmentStatus;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    public List<AppointmentResponseDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream()
                .map(appointmentMapper::toAppointmentResponseDto)
                .collect(Collectors.toList());
    }

    public AppointmentResponseDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("appointment not found"));
        return appointmentMapper.toAppointmentResponseDto(appointment);
    }

    public AppointmentResponseDto createAppointment(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.toAppointment(appointmentCreateDto);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponseDto(savedAppointment);
    }

    public AppointmentResponseDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("appointment not found"));

        appointmentMapper.updateAppointmentFromDto(appointmentUpdateDto, existingAppointment);
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return appointmentMapper.toAppointmentResponseDto(updatedAppointment);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus newStatus, User user) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        validateUserRoleForStatusUpdate(user);
        validateStatusChange(appointment.getAppointmentStatus(), newStatus);
        return appointmentRepository.save(appointment);
    }

    private void validateUserRoleForStatusUpdate(User user) {
        if (user.getUserRole() != UserRole.ADMIN && user.getUserRole() != UserRole.PSYCHOLOGIST) {
            throw new SecurityException("You do not have permission to update this appointment");
        }
    }

    private void validateStatusChange(AppointmentStatus currentStatus, AppointmentStatus newStatus) {
        if (currentStatus == AppointmentStatus.CANCELED || currentStatus == AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Can not change status from " + currentStatus + newStatus);
        }
        if (currentStatus == AppointmentStatus.PENDING && newStatus == AppointmentStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "Cannot change status from PENDING to SCHEDULED. Only COMPLETED or CANCELED is allowed.");
        }
    }

    public void deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Appointment not found");
        }
    }

    public List<AppointmentResponseDto> findByAppointmentDateAfter(LocalDateTime appointmentDate) {
       List<Appointment> appointments = appointmentRepository.findByAppointmentDateAfter(appointmentDate);
       return appointmentMapper.toAppointmentResponseDtoList(appointments);
    }

    public List<AppointmentResponseDto> findByAppointmentDateBetween(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2) {
       List<Appointment> appointments = appointmentRepository.findByAppointmentDateBetween(appointmentDate1, appointmentDate2);
       return appointmentMapper.toAppointmentResponseDtoList(appointments);
    }

    public List<AppointmentResponseDto> findByStatus(AppointmentStatus status) {
       List<Appointment> appointments = appointmentRepository.findByStatus(status);
       return appointmentMapper.toAppointmentResponseDtoList(appointments);
    }

    public List<AppointmentResponseDto> findByAppointmentDateBetweenAndStatus(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, AppointmentStatus status) {
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateBetweenAndStatus(appointmentDate1, appointmentDate2, status);
        return appointmentMapper.toAppointmentResponseDtoList(appointments);
    }

    public List<AppointmentResponseDto> findAppointmentByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        return appointmentMapper.toAppointmentResponseDtoList(appointments);
    }
}
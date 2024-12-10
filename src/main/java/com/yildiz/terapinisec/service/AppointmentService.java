package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.Appointment;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.AppointmentRepository;
import com.yildiz.terapinisec.util.AppointmentStatus;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
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

    List<Appointment> findByAppointmentDateAfter(LocalDateTime appointmentDate) {
        return appointmentRepository.findByAppointmentDateAfter(appointmentDate);
    }

    Page<Appointment> findByAppointmentDateBetween(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, Pageable pageable) {
        return appointmentRepository.findByAppointmentDateBetween(appointmentDate1, appointmentDate2, pageable);
    }

    List<Appointment> findByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    Page<Appointment> findByAppointmentDateBetweenAndStatus(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, AppointmentStatus status, Pageable pageable) {
        return appointmentRepository.findByAppointmentDateBetweenAndStatus(appointmentDate1, appointmentDate2, status, pageable);
    }

    List<Appointment> findByUserId(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }
}
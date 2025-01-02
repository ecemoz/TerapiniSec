package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Appointment;
import com.yildiz.terapinisec.util.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment>findByAppointmentDateAfter(LocalDateTime appointmentDate);
    List<Appointment> findByAppointmentDateBetween(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2);
    List<Appointment>findByStatus(AppointmentStatus status);
    List<Appointment>findByAppointmentDateBetweenAndStatus(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, AppointmentStatus status);
    List<Appointment>findByUserId(Long userId);
}
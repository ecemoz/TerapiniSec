package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.Appointment;
import com.yildiz.terapinisec.util.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment>findByAppointmentDateAfter(LocalDateTime appointmentDate);
    Page<Appointment> findByAppointmentDateBetween(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, Pageable pageable);
    List<Appointment>findByStatus(AppointmentStatus status);
    Page<Appointment>findByAppointmentDateBetweenAndStatus(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, AppointmentStatus status, Pageable pageable);
    List<Appointment>findByUserId(Long userId);
}
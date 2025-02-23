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
    List<Appointment>findByAppointmentStatus(AppointmentStatus appointmentStatus);
    List<Appointment>findByAppointmentDateBetweenAndAppointmentStatus(LocalDateTime appointmentDate1, LocalDateTime appointmentDate2, AppointmentStatus status);
    List<Appointment> findByAppointmentClients_Id(Long clientId);
    List<Appointment> findByTherapist_Id(Long therapistId);
    boolean existsByIdAndAppointmentClients_Id(Long id, Long appointmentClientsId);
}
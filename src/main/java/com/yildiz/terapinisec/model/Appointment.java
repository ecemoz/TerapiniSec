package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name= "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus = AppointmentStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User appointmentClients;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id",nullable = false)
    private User therapist;
}

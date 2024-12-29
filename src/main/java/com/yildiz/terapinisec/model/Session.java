package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.SessionType;
import com.yildiz.terapinisec.util.SessionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionName;

    @Column(nullable = false)
    private LocalDateTime sessionDateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    @Column(nullable = false)
    private  int durationMinutes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus = SessionStatus.SCHEDULED;

    @OneToMany(mappedBy = "session" , fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants;
}
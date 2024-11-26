package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.SleepQuality;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sleeplogs")
public class SleepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private Duration sleepDuration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SleepQuality sleepQuality;

    @Column(nullable = false)
    private LocalDateTime sleepDate;

    @PrePersist
    protected void onCreate() {
        this.sleepDate = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User sleeper;
}
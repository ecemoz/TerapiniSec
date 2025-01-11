package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.SleepQuality;
import jakarta.persistence.*;
import lombok.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sleeplogs")
public class SleepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Duration sleepDuration;

    @Column(nullable = false)
    private int sleepQualityValue;

    @Column(nullable = false)
    private LocalDateTime sleepDate;

    @PrePersist
    protected void onCreate() {
        this.sleepDate = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User sleeper;

    public SleepQuality getSleepQuality() {
        return SleepQuality.fromValue(this.sleepQualityValue);
    }

    public void setSleepQuality(SleepQuality sleepQuality) {
        this.sleepQualityValue = sleepQuality.getValue();
    }
}
package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.MedidationSessionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "meditationsessions")
public class MedidationSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private MedidationSessionType medidationSessionType;

    @Column(nullable = false)
    private Duration medidationSessionDuration;

    @Column(nullable = false)
    private LocalDateTime medidationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User meditator;
}

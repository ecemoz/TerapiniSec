package com.yildiz.terapinisec.model;

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
@Table(name = "meditationsessions")
public class MeditationSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Duration meditationSessionDuration;

    @Column(nullable = false)
    private LocalDateTime meditationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User meditator;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name ="content_id",nullable = false)
    private MeditationContent meditationContent;
}
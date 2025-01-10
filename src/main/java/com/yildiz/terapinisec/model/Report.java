package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.ReportSituation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportSituation reportSituation;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime reportCreatedAt;

    @PrePersist
    protected void onCreate() {
        reportCreatedAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id",nullable = false)
    private User reportOwner;
}
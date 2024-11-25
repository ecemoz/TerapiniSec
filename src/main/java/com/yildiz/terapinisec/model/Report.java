package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.ReportSituation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
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

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User reportOwner;
}
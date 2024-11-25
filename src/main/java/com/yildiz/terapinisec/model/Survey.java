package com.yildiz.terapinisec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime surveyCreatedAt;

    @PrePersist
    protected void onCreate() {
        this.surveyCreatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "survey" , fetch = FetchType.LAZY)
    private List<SurveyResponse> surveyResponses;
}
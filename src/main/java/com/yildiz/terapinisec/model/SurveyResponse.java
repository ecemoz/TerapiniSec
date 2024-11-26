package com.yildiz.terapinisec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "surveyresponses")
public class SurveyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String responses;

    @Column(nullable = false)
    private LocalDateTime submittedDate;

    @PrePersist
    protected void onCreate() {
        submittedDate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User responsedBy;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;
}

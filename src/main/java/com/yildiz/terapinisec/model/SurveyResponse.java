package com.yildiz.terapinisec.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
        this.submittedDate = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User responsedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id",nullable = false)
    private Survey survey;
}

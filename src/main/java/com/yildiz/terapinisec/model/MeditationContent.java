package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.MeditationSessionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "meditationcontents")
public class MeditationContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private MeditationSessionType meditationSessionType;

    @Column(nullable = false,unique = true)
    private String contentUrl;

    @Column(nullable = false)
    private boolean isPublic;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
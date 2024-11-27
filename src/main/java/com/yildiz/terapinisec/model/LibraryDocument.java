package com.yildiz.terapinisec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "librarydocuments")
public class LibraryDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true, nullable = false)
    private String title ;

    @Column(unique = true, nullable = false)
    private String description ;

    @Column(nullable = false)
    private String documentUrl;

    @Column(nullable = false)
    private LocalDateTime documentUploadDate;

    @PrePersist
    protected void onCreate() {
        documentUploadDate = LocalDateTime.now();
    }

    @Column(nullable = false)
    private boolean isPublic = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id",nullable = false)
    private User fileUploader;
}
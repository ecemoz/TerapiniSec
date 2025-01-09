package com.yildiz.terapinisec.model;

import com.yildiz.terapinisec.util.FileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "filestorage")
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true, nullable = false)
    private String fileName ;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType ;

    @Column(nullable = false,unique = true)
    private String fileUrl;

    @Column(nullable = false)
    private LocalDateTime fileUploadDate ;

    @PrePersist
    protected void onCreate() {
        fileUploadDate = LocalDateTime.now();
    }

    @Column(nullable = false)
    private float fileSize;

    @Column(nullable = false)
    private boolean isFilePublic = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_uploader_id", nullable = false)
    private User documentUploader;
}
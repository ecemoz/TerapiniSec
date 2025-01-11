package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.FileStorage;
import com.yildiz.terapinisec.util.FileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {

    Page<FileStorage>findByDocumentUploaderId(Long uploaderId, Pageable pageable);
    List<FileStorage> findByFileType(FileType fileType);
    List<FileStorage> findByFileNameContaining(String fileNameKeyWord);
    List<FileStorage>findByDocumentUploaderIdAndFileType(Long uploaderId, FileType fileType);
    List<FileStorage>findByIsPublicTrue();
    List<FileStorage>findByDocumentUploaderIdAndIsPublicFalse(Long uploaderId);
    List<FileStorage>findByFileUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<FileStorage>findByDocumentUploaderIdAndFileUploadDateBetween(Long uploaderId, LocalDateTime startDate, LocalDateTime endDate);
    FileStorage findByFileName(String fileName);
}
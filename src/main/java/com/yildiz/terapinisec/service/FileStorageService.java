package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.FileStorage;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.FileStorageRepository;
import com.yildiz.terapinisec.util.FileType;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final Path rootLocation = Paths.get("file-storage");

    @PostConstruct
    public void initStorage() {
        try {
            Files.createDirectories(rootLocation.resolve("documents"));
            Files.createDirectories(rootLocation.resolve("audio"));
            Files.createDirectories(rootLocation.resolve("video"));
            Files.createDirectories(rootLocation.resolve("images"));
        }  catch (IOException e) {
            throw new RuntimeException("Could not initialize file storage", e);
        }
    }

    public FileStorage uploadFile(MultipartFile file, FileType fileType, User documentUploader ) {
        try {
            Path directory = rootLocation.resolve (fileType.toString().toLowerCase());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            String fileName = UUID.randomUUID()+ "_" + file.getOriginalFilename();
            Path destination = directory.resolve(fileName);
            Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);

            FileStorage fileStorage = new FileStorage();
            fileStorage.setFileName(fileName);
            fileStorage.setFileType(fileType);
            fileStorage.setFilePath(fileType.toString().toLowerCase() + "/" + fileName);
            fileStorage.setDocumentUploader(documentUploader);
            fileStorage.setFileUploadDate(LocalDateTime.now());

            return fileStorageRepository.save(fileStorage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file !", e);
        }
    }

    public Resource downloadFile(String filePath) {
        try {
            Path file = rootLocation.resolve(filePath).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            }else {
                throw new RuntimeException("File not found " + filePath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to store file !", e);
        }
    }

    public void deleteFile(Long fileId) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId)
                .orElseThrow(()-> new RuntimeException("File not found with ID: " + fileId));
        try {
            Path file = rootLocation.resolve(fileStorage.getFilePath()).normalize();
            Files.deleteIfExists(file);
            fileStorageRepository.delete(fileStorage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file:" +fileStorage.getFilePath(), e);
        }
    }

    Page<FileStorage> findByUploaderId(Long uploaderId, Pageable pageable) {
        return fileStorageRepository.findByUploaderId(uploaderId, pageable);
    }

    List<FileStorage> findByFileType(FileType fileType) {
        return fileStorageRepository.findByFileType(fileType);
    }

    List<FileStorage>findByFileNameContainingOrDescriptionContaining(String fileNameKeyWord, Pageable pageable) {
        return fileStorageRepository.findByFileNameContainingOrDescriptionContaining(fileNameKeyWord, pageable);
    }
    List<FileStorage>findByUploaderIdAndFileType(Long uploaderId, FileType fileType) {
        return fileStorageRepository.findByUploaderIdAndFileType(uploaderId, fileType);
    }
    List<FileStorage>findByIsPublicTrue() {
        return fileStorageRepository.findByIsPublicTrue();
    }
    List<FileStorage>findByUploaderIdAndIsPublicFalse(Long uploaderId) {
        return fileStorageRepository.findByUploaderIdAndIsPublicFalse(uploaderId);
    }
    List<FileStorage>findByUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return fileStorageRepository.findByUploadDateBetween(startDate, endDate);
    }
    List<FileStorage>findByUploaderIdAndUploadDateBetween(Long uploaderId, LocalDateTime startDate, LocalDateTime endDate) {
        return fileStorageRepository.findByUploaderIdAndUploadDateBetween(uploaderId, startDate, endDate);
    }
    FileStorage findByFileName(String fileName) {
        return fileStorageRepository.findByFileName(fileName);
    }
}
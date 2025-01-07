package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.FileStorageCreateDto;
import com.yildiz.terapinisec.dto.FileStorageResponseDto;
import com.yildiz.terapinisec.mapper.FileStorageMapper;
import com.yildiz.terapinisec.model.FileStorage;
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
    private final FileStorageMapper fileStorageMapper;
    private final Path rootLocation = Paths.get("file-storage");

    @PostConstruct
    public void initStorage() {
        try {
            for(FileType fileType : FileType.values()) {
                Files.createDirectories(rootLocation.resolve(fileType.toString().toLowerCase()));
            }
        }  catch (IOException e) {
            throw new RuntimeException("Could not initialize file storage", e);
        }
    }

    public FileStorageResponseDto uploadFile(MultipartFile file, FileStorageCreateDto fileStorageCreateDto ) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path directory = rootLocation.resolve(fileStorageCreateDto.getFileType().toString().toLowerCase());
        Path destination = directory.resolve(fileName);

        try {
            Files.createDirectories(directory);
            Files.copy(file.getInputStream(), destination , StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(" Failed to store file!", e);
        }

        FileStorage fileStorage = fileStorageMapper.toFileStorage(fileStorageCreateDto);
        fileStorage.setFileName(fileName);
        fileStorage.setFilePath(fileStorageCreateDto.getFileType().toString().toLowerCase() + "/" + fileName);
        fileStorage.setFileUploadDate(LocalDateTime.now());

        FileStorage savedFileStorage = fileStorageRepository.save(fileStorage);
        return fileStorageMapper.toFileStorageResponseDto(savedFileStorage);
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

    public Page<FileStorageResponseDto> findByUploaderId(Long uploaderId, Pageable pageable) {
        return fileStorageRepository.findByUploaderId(uploaderId, pageable)
                .map(fileStorageMapper::toFileStorageResponseDto);
    }

    public List<FileStorageResponseDto> findByFileType(FileType fileType) {
        List<FileStorage> fileStorages = fileStorageRepository.findByFileType(fileType);
        return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }

    public List<FileStorageResponseDto>findByFileNameContainingOrDescriptionContaining(String fileNameKeyWord, Pageable pageable) {
        List<FileStorage> fileStorages = fileStorageRepository.findByFileNameContainingOrDescriptionContaining(fileNameKeyWord, pageable);
        return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }
    public List<FileStorageResponseDto>findByUploaderIdAndFileType(Long uploaderId, FileType fileType) {
        List<FileStorage> fileStorages = fileStorageRepository.findByUploaderIdAndFileType(uploaderId, fileType);
        return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }
    public List<FileStorageResponseDto>findByIsPublicTrue() {
        List<FileStorage> fileStorages = fileStorageRepository.findByIsPublicTrue();
        return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }

    public List<FileStorageResponseDto>findByUploaderIdAndIsPublicFalse(Long uploaderId) {
       List<FileStorage> fileStorages = fileStorageRepository.findByUploaderIdAndIsPublicFalse(uploaderId);
       return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }
    public List<FileStorageResponseDto>findByUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
       List<FileStorage> fileStorages = fileStorageRepository.findByUploadDateBetween(startDate, endDate);
       return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }
    public List<FileStorageResponseDto>findByUploaderIdAndUploadDateBetween(Long uploaderId, LocalDateTime startDate, LocalDateTime endDate) {
        List<FileStorage> fileStorages = fileStorageRepository.findByUploaderIdAndUploadDateBetween(uploaderId, startDate, endDate);
        return fileStorageMapper.toFileStorageResponseDtoList(fileStorages);
    }
    public FileStorageResponseDto findByFileName(String fileName) {
        FileStorage fileStorages = fileStorageRepository.findByFileName(fileName);
        return fileStorageMapper.toFileStorageResponseDto(fileStorages);
    }
}
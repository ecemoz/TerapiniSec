package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.FileStorageCreateDto;
import com.yildiz.terapinisec.dto.FileStorageResponseDto;
import com.yildiz.terapinisec.dto.FileStorageUpdateDto;
import com.yildiz.terapinisec.model.FileStorage;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileStorageMapper {


    public FileStorage toFileStorage(FileStorageCreateDto createDto, User uploader) {
        if (createDto == null || uploader == null) {
            return null;
        }

        return FileStorage.builder()
                .fileName(createDto.getFileName())
                .filePath(createDto.getFilePath())
                .fileType(createDto.getFileType())
                .fileUrl(createDto.getFileUrl())
                .fileSize(createDto.getFileSize())
                .isFilePublic(createDto.isFilePublic())
                .documentUploader(uploader)
                .build();
    }

    // Model -> Response DTO dönüşümü
    public FileStorageResponseDto toFileStorageResponseDto(FileStorage fileStorage) {
        if (fileStorage == null) {
            return null;
        }

        return FileStorageResponseDto.builder()
                .id(fileStorage.getId())
                .fileName(fileStorage.getFileName())
                .filePath(fileStorage.getFilePath())
                .fileType(fileStorage.getFileType())
                .fileUrl(fileStorage.getFileUrl())
                .fileUploadDate(fileStorage.getFileUploadDate())
                .fileSize(fileStorage.getFileSize())
                .isFilePublic(fileStorage.isFilePublic())
                .documentUploaderUsername(fileStorage.getDocumentUploader() != null
                        ? fileStorage.getDocumentUploader().getUsername()
                        : null)
                .build();
    }

    // Update DTO -> Model dönüşümü
    public void updateFileStorageFromDto(FileStorageUpdateDto updateDto, FileStorage fileStorage) {
        if (updateDto == null || fileStorage == null) {
            return;
        }

        fileStorage.setFileName(updateDto.getFileName());
        fileStorage.setFilePath(updateDto.getFilePath());
        fileStorage.setFileType(updateDto.getFileType());
        fileStorage.setFileUrl(updateDto.getFileUrl());
        fileStorage.setFileSize(updateDto.getFileSize());
        fileStorage.setFilePublic(updateDto.isFilePublic());
    }

    public List<FileStorageResponseDto> toFileStorageResponseDtoList(List<FileStorage> fileStorages) {
        if (fileStorages == null || fileStorages.isEmpty()) {
            return List.of();
        }

        return fileStorages.stream()
                .map(this::toFileStorageResponseDto)
                .collect(Collectors.toList());
    }
}

package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.LibraryDocumentCreateDto;
import com.yildiz.terapinisec.dto.LibraryDocumentResponseDto;
import com.yildiz.terapinisec.dto.LibraryDocumentUpdateDto;
import com.yildiz.terapinisec.model.LibraryDocument;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryDocumentMapper {

    public LibraryDocument toLibraryDocument(LibraryDocumentCreateDto createDto, User uploader) {
        if (createDto == null || uploader == null) {
            return null;
        }

        return LibraryDocument.builder()
                .title(createDto.getTitle())
                .description(createDto.getDescription())
                .documentUrl(createDto.getDocumentUrl())
                .isPublic(createDto.isPublic())
                .accesibleByPremiumOnly(createDto.isAccesibleByPremiumOnly())
                .fileUploader(uploader)
                .build();
    }

    public LibraryDocumentResponseDto toLibraryDocumentResponseDto(LibraryDocument libraryDocument) {
        if (libraryDocument == null) {
            return null;
        }

        return LibraryDocumentResponseDto.builder()
                .id(libraryDocument.getId())
                .title(libraryDocument.getTitle())
                .description(libraryDocument.getDescription())
                .documentUrl(libraryDocument.getDocumentUrl())
                .documentUploadDate(libraryDocument.getDocumentUploadDate())
                .isPublic(libraryDocument.isPublic())
                .accesibleByPremiumOnly(libraryDocument.isAccesibleByPremiumOnly())
                .fileUploaderUsername(libraryDocument.getFileUploader() != null
                        ? libraryDocument.getFileUploader().getUsername()
                        : null)
                .build();
    }

    public void updateLibraryDocumentFromDto(LibraryDocumentUpdateDto updateDto, LibraryDocument libraryDocument) {
        if (updateDto == null || libraryDocument == null) {
            return;
        }

        libraryDocument.setTitle(updateDto.getTitle());
        libraryDocument.setDescription(updateDto.getDescription());
        libraryDocument.setDocumentUrl(updateDto.getDocumentUrl());
        libraryDocument.setPublic(updateDto.isPublic());
        libraryDocument.setAccesibleByPremiumOnly(updateDto.isAccesibleByPremiumOnly());
    }

    public List<LibraryDocumentResponseDto> toLibraryDocumentResponseDtoList(List<LibraryDocument> libraryDocuments) {
        if (libraryDocuments == null || libraryDocuments.isEmpty()) {
            return List.of();
        }

        return libraryDocuments.stream()
                .map(this::toLibraryDocumentResponseDto)
                .collect(Collectors.toList());
    }
}

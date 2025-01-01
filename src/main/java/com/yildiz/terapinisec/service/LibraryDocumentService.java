package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.LibraryDocumentCreateDto;
import com.yildiz.terapinisec.dto.LibraryDocumentResponseDto;
import com.yildiz.terapinisec.dto.LibraryDocumentUpdateDto;
import com.yildiz.terapinisec.mapper.LibraryDocumentMapper;
import com.yildiz.terapinisec.model.LibraryDocument;
import com.yildiz.terapinisec.repository.LibraryDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryDocumentService {

    @Autowired
    private LibraryDocumentRepository libraryDocumentRepository;

    @Autowired
    private LibraryDocumentMapper libraryDocumentMapper;

    public Page<LibraryDocumentResponseDto> getAllLibraryDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<LibraryDocument> documents = libraryDocumentRepository.findAll(pageable);
        return documents.map(libraryDocumentMapper::toLibraryDocumentResponseDto);
    }

    public LibraryDocumentResponseDto getLibraryDocumentById(Long id) {
        LibraryDocument document = libraryDocumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid library document"));
        return libraryDocumentMapper.toLibraryDocumentResponseDto(document);
    }

    public LibraryDocumentResponseDto createLibraryDocument(LibraryDocumentCreateDto libraryDocumentCreateDto) {
        LibraryDocument libraryDocument = libraryDocumentMapper.toLibraryDocument(libraryDocumentCreateDto);
        LibraryDocument savedDocument = libraryDocumentRepository.save(libraryDocument);
        return libraryDocumentMapper.toLibraryDocumentResponseDto(savedDocument);
    }

    public LibraryDocumentResponseDto updateLibraryDocument(Long id , LibraryDocumentUpdateDto libraryDocumentUpdateDto) {
        LibraryDocument existingDocument = libraryDocumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid library document"));

        libraryDocumentMapper.updateLibraryDocumentFromDto(libraryDocumentUpdateDto, existingDocument);
        LibraryDocument savedDocument = libraryDocumentRepository.save(existingDocument);
        return libraryDocumentMapper.toLibraryDocumentResponseDto(savedDocument);
    }

    public void deleteLibraryDocument(Long id) {
        if (libraryDocumentRepository.existsById(id)) {
            libraryDocumentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Document not found.");
        }
    }

    public Page<LibraryDocumentResponseDto>findByIsPublicTrue(Pageable pageable) {
        Page<LibraryDocument> documents = libraryDocumentRepository.findByIsPublicTrue(pageable);
        return documents.map(libraryDocumentMapper::toLibraryDocumentResponseDto);
    }

    public Page<LibraryDocumentResponseDto>findByUploaderId(Long uploaderId, Pageable pageable) {
        Page<LibraryDocument> documents = libraryDocumentRepository.findByUploaderId(uploaderId, pageable);
        return documents.map(libraryDocumentMapper::toLibraryDocumentResponseDto);
    }

    public List<LibraryDocumentResponseDto> findByUploaderIdAndIsPublicTrue(Long uploaderId) {
        List<LibraryDocument> documents = libraryDocumentRepository.findByUploaderIdAndIsPublicTrue(uploaderId);
        return documents.stream()
                .map(libraryDocumentMapper::toLibraryDocumentResponseDto)
                .collect(Collectors.toList());
    }

    public List<LibraryDocumentResponseDto>findByTitleContainingOrDescriptionContaining(String titleKeyword, String descriptionKeyword) {
       List<LibraryDocument> documents = libraryDocumentRepository.findByTitleContainingOrDescriptionContaining(titleKeyword, descriptionKeyword);
       return documents.stream()
               .map(libraryDocumentMapper::toLibraryDocumentResponseDto)
               .collect(Collectors.toList());
    }

    public List<LibraryDocumentResponseDto>findByAccesibleByPremiumOnlyTrue() {
        List<LibraryDocument> documents = libraryDocumentRepository.findByAccesibleByPremiumOnlyTrue();
        return documents.stream()
                .map(libraryDocumentMapper::toLibraryDocumentResponseDto)
                .collect(Collectors.toList());
    }

    public List<LibraryDocumentResponseDto>findByUploaderIdAndAccessibleByPremiumOnlyTrue(Long uploaderId) {
        List<LibraryDocument> documents = libraryDocumentRepository.findByUploaderIdAndAccessibleByPremiumOnlyTrue(uploaderId);
        return documents.stream()
                .map(libraryDocumentMapper::toLibraryDocumentResponseDto)
                .collect(Collectors.toList());
    }
}
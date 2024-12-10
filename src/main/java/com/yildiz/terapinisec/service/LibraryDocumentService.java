package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.LibraryDocument;
import com.yildiz.terapinisec.repository.LibraryDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryDocumentService {

    @Autowired
    private LibraryDocumentRepository libraryDocumentRepository;

    public Page<LibraryDocument> getAllLibraryDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return libraryDocumentRepository.findAll(pageable);
    }

    public Optional<LibraryDocument> getLibraryDocumentById(Long id) {
        return libraryDocumentRepository.findById(id);
    }

    public LibraryDocument createLibraryDocument(LibraryDocument libraryDocument) {
        libraryDocument.setTitle(libraryDocument.getTitle());
        libraryDocument.setDescription(libraryDocument.getDescription());
        libraryDocument.setDocumentUrl(libraryDocument.getDocumentUrl());
        libraryDocument.setDocumentUploadDate(libraryDocument.getDocumentUploadDate());
        libraryDocument.setPublic(libraryDocument.isPublic());
        libraryDocument.setAccesibleByPremiumOnly(libraryDocument.isAccesibleByPremiumOnly());
        libraryDocument.setFileUploader(libraryDocument.getFileUploader());
        return libraryDocumentRepository.save(libraryDocument);
    }

    public LibraryDocument updateLibraryDocument(Long id , LibraryDocument updatedLibraryDocument) {
        return libraryDocumentRepository.findById(id)
                .map(libraryDocument -> {
                    libraryDocument.setTitle(updatedLibraryDocument.getTitle());
                    libraryDocument.setDescription(updatedLibraryDocument.getDescription());
                    libraryDocument.setDocumentUrl(updatedLibraryDocument.getDocumentUrl());
                    libraryDocument.setDocumentUploadDate(updatedLibraryDocument.getDocumentUploadDate());
                    libraryDocument.setPublic(updatedLibraryDocument.isPublic());
                    return libraryDocumentRepository.save(libraryDocument);
                })
                .orElseThrow(() -> new RuntimeException("Document not found."));
    }

    public void deleteLibraryDocument(Long id) {
        if (libraryDocumentRepository.existsById(id)) {
            libraryDocumentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Document not found.");
        }
    }

    public Page<LibraryDocument>findByIsPublicTrue(Pageable pageable) {
        return libraryDocumentRepository.findByIsPublicTrue(pageable);
    }

    public Page<LibraryDocument>findByUploaderId(Long uploaderId, Pageable pageable) {
        return libraryDocumentRepository.findByUploaderId(uploaderId, pageable);
    }

    public List<LibraryDocument> findByUploaderIdAndIsPublicTrue(Long uploaderId) {
        return libraryDocumentRepository.findByUploaderIdAndIsPublicTrue(uploaderId);
    }

    public List<LibraryDocument>findByTitleContainingOrDescriptionContaining(String titleKeyword, String descriptionKeyword) {
        return libraryDocumentRepository.findByTitleContainingOrDescriptionContaining(titleKeyword, descriptionKeyword);
    }

    public List<LibraryDocument>findByAccesibleByPremiumOnlyTrue() {
        return libraryDocumentRepository.findByAccesibleByPremiumOnlyTrue();
    }

    public List<LibraryDocument>findByUploaderIdAndAccessibleByPremiumOnlyTrue(Long uploaderId) {
        return libraryDocumentRepository.findByUploaderIdAndAccessibleByPremiumOnlyTrue(uploaderId);
    }
}
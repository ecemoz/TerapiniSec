package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.LibraryDocumentCreateDto;
import com.yildiz.terapinisec.dto.LibraryDocumentResponseDto;
import com.yildiz.terapinisec.dto.LibraryDocumentUpdateDto;
import com.yildiz.terapinisec.service.LibraryDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/librarydocuments")
public class LibraryDocumentController {

    @Autowired
    private LibraryDocumentService libraryDocumentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<LibraryDocumentResponseDto>> getAllLibraryDocuments(int page, int size) {
        Page<LibraryDocumentResponseDto> libraryDocuments = libraryDocumentService.getAllLibraryDocuments(page, size);
        return ResponseEntity.ok(libraryDocuments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LibraryDocumentResponseDto> getLibraryDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(libraryDocumentService.getLibraryDocumentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<LibraryDocumentResponseDto> createLibraryDocument(@RequestBody LibraryDocumentCreateDto libraryDocumentCreateDto) {
        return ResponseEntity.ok(libraryDocumentService.createLibraryDocument(libraryDocumentCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<LibraryDocumentResponseDto> updateLibraryDocument(@PathVariable Long id, @RequestBody LibraryDocumentUpdateDto libraryDocumentUpdateDto) {
        return ResponseEntity.ok(libraryDocumentService.updateLibraryDocument(id, libraryDocumentUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<LibraryDocumentResponseDto> deleteLibraryDocument(@PathVariable Long id) {
        libraryDocumentService.deleteLibraryDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public-true")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<LibraryDocumentResponseDto>> getLibraryDocumentPublicTrue(@RequestParam Pageable pageable) {
        return ResponseEntity.ok(libraryDocumentService.findByIsPublicTrue(pageable));
    }

    @GetMapping("/{uploaderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<LibraryDocumentResponseDto>> getLibraryDocumentByUploaderId(@PathVariable Long uploaderId , @RequestParam Pageable pageable) {
        return ResponseEntity.ok(libraryDocumentService.findByUploaderId(uploaderId, pageable));
    }

    @GetMapping("/{uploaderId}/public-true")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LibraryDocumentResponseDto>> getUploaderIdAndIsPublicTrue(@PathVariable Long uploaderId ) {
        return ResponseEntity.ok(libraryDocumentService.findByUploaderIdAndIsPublicTrue(uploaderId));
    }

    @GetMapping("/title/description")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LibraryDocumentResponseDto>> getLibraryDocumentByTitleContainingOrDescriptionContaining(@RequestParam String titleKeyword, @RequestParam String descriptionKeyword) {
        return ResponseEntity.ok(libraryDocumentService.findByTitleContainingOrDescriptionContaining(titleKeyword, descriptionKeyword));
    }

    @GetMapping("/premiumonly")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isPremiumUser()")
    public ResponseEntity<List<LibraryDocumentResponseDto>> getLibraryDocumentByPremiumOnlyTrue() {
        return ResponseEntity.ok(libraryDocumentService.findByAccesibleByPremiumOnlyTrue());
    }

    @GetMapping("/{uploaderId}/premiumonly")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isPremiumUser()")
    public ResponseEntity<List<LibraryDocumentResponseDto>> getUploaderIdAndIsPremiumOnly(@PathVariable Long uploaderId ) {
        return ResponseEntity.ok(libraryDocumentService.findByUploaderIdAndAccessibleByPremiumOnlyTrue(uploaderId));
    }
}
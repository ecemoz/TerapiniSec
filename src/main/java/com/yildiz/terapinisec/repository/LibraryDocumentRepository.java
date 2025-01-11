package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.LibraryDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibraryDocumentRepository extends JpaRepository<LibraryDocument, Long> {

    Page<LibraryDocument> findByIsPublicTrue(Pageable pageable);
    Page<LibraryDocument> findByFileUploaderId(Long uploaderId, Pageable pageable);
    List<LibraryDocument> findByFileUploaderIdAndIsPublicTrue(Long uploaderId);
    List<LibraryDocument> findByTitleContainingOrDescriptionContaining(String titleKeyword, String descriptionKeyword);
    List<LibraryDocument> findByAccesibleByPremiumOnlyTrue();
    List<LibraryDocument> findByFileUploaderIdAndAccesibleByPremiumOnlyTrue(Long uploaderId);

}
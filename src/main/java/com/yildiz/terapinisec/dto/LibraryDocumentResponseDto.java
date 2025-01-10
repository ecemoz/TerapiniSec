package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class LibraryDocumentResponseDto {

    private Long id;
    private String title;
    private String description;
    private String documentUrl;
    private LocalDateTime documentUploadDate;
    private boolean isPublic;
    private boolean accesibleByPremiumOnly;
    private String fileUploaderUsername;
}

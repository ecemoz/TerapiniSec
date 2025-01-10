package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibraryDocumentCreateDto {

    private String title;
    private String description;
    private String documentUrl;
    private boolean isPublic;
    private boolean accesibleByPremiumOnly;
    private Long fileUploaderId;
}
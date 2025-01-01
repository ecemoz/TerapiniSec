package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class LibraryDocumentCreateDto {

    private String title;
    private String description;
    private String documentUrl;
    private boolean isPublic;
    private boolean accesibleByPremiumOnly;
    private Long fileUploaderId;
}

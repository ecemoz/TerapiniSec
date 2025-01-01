package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class LibraryDocumentUpdateDto {

    private String title;
    private String description;
    private String documentUrl;
    private boolean isPublic;
    private boolean accesibleByPremiumOnly;
}
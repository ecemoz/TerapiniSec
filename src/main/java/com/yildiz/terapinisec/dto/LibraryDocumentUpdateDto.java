package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibraryDocumentUpdateDto {

    private String title;
    private String description;
    private String documentUrl;
    private boolean isPublic;
    private boolean accesibleByPremiumOnly;
}
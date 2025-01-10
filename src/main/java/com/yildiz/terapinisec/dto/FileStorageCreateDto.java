package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.FileType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileStorageCreateDto {

    private String fileName;
    private String filePath;
    private FileType fileType;
    private String fileUrl;
    private float fileSize;
    private boolean isFilePublic;
    private Long documentUploaderId;
}
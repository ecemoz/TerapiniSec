package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.FileType;
import lombok.Data;

@Data
public class FileStorageUpdateDto {

    private String fileName;
    private String filePath;
    private FileType fileType;
    private String fileUrl;
    private float fileSize;
    private boolean isFilePublic;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.FileType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class FileStorageResponseDto {

    private Long id;
    private String fileName;
    private String filePath;
    private FileType fileType;
    private String fileUrl;
    private LocalDateTime fileUploadDate;
    private float fileSize;
    private boolean isFilePublic;
    private String documentUploaderUsername;
}
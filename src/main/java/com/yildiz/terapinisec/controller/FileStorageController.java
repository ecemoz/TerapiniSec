package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.FileStorageCreateDto;
import com.yildiz.terapinisec.dto.FileStorageResponseDto;
import com.yildiz.terapinisec.service.FileStorageService;
import com.yildiz.terapinisec.util.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<FileStorageResponseDto> uploadFile
            (@RequestParam("file") MultipartFile file, @RequestBody FileStorageCreateDto fileStorageCreateDto) {
        return ResponseEntity.ok(fileStorageService.uploadFile(file, fileStorageCreateDto));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filePath) {
        Resource resource = fileStorageService.downloadFile(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileStorageService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{uploaderId}")
    public ResponseEntity<Page<FileStorageResponseDto>> getFileByUploaderId(@PathVariable Long uploaderId , Pageable pageable) {
        return ResponseEntity.ok(fileStorageService.findByUploaderId(uploaderId, pageable));
    }

    @GetMapping("/{filetype}")
    public ResponseEntity<List<FileStorageResponseDto>> getFileByFiletype(@PathVariable FileType filetype) {
        return ResponseEntity.ok(fileStorageService.findByFileType(filetype));
    }

    @GetMapping("/filename-description")
    public ResponseEntity<List<FileStorageResponseDto>> getFileByFileNameContainingOrDescriptionContaining(@RequestParam String fileNameKeyWord) {
        return ResponseEntity.ok(fileStorageService.findByFileNameContainingOrDescriptionContaining(fileNameKeyWord));
    }

    @GetMapping("/{uploaderId}/filetype")
    public ResponseEntity<List<FileStorageResponseDto>> getFileByUploaderIdAndFileType(@PathVariable Long uploaderId,@RequestParam  FileType fileType) {
        return ResponseEntity.ok(fileStorageService.findByUploaderIdAndFileType(uploaderId, fileType));
    }

    @GetMapping("/public-true")
    public ResponseEntity<List<FileStorageResponseDto>> getFileByPublicTrue() {
        return ResponseEntity.ok(fileStorageService.findByIsPublicTrue());
    }

    @GetMapping("/{uploaderId}/public-false")
    public ResponseEntity<List<FileStorageResponseDto>> getPublicFalseFileByUploaderId(@PathVariable Long uploaderId) {
        return ResponseEntity.ok(fileStorageService.findByUploaderIdAndIsPublicFalse(uploaderId));
    }

    @GetMapping("/date-between")
    public ResponseEntity<List<FileStorageResponseDto>> getFileByDateBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(fileStorageService.findByUploadDateBetween(startDate, endDate));
    }

    @GetMapping("/{uploaderId}/date-between")
    public ResponseEntity<List<FileStorageResponseDto>> getFileByUploaderIdAndUploadDateBetween(@PathVariable Long uploaderId, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(fileStorageService.findByUploaderIdAndUploadDateBetween(uploaderId, startDate, endDate));
    }

    @GetMapping("/filename")
    public ResponseEntity<FileStorageResponseDto>getFileByFilename(@RequestParam String filename) {
        return ResponseEntity.ok(fileStorageService.findByFileName(filename));
    }
}
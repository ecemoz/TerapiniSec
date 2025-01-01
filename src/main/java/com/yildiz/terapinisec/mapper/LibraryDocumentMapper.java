package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.LibraryDocumentCreateDto;
import com.yildiz.terapinisec.dto.LibraryDocumentResponseDto;
import com.yildiz.terapinisec.dto.LibraryDocumentUpdateDto;
import com.yildiz.terapinisec.model.LibraryDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibraryDocumentMapper {

    @Mapping(source = "fileUploaderId" , target = "fileUploader.id")
    LibraryDocument toLibraryDocument(LibraryDocumentCreateDto libraryDocumentCreateDto);

    @Mapping(source = "fileUploader.username" , target = "fileUploaderUsername")
    LibraryDocumentResponseDto toLibraryDocumentResponseDto(LibraryDocument libraryDocument);

    void updateLibraryDocumentFromDto(LibraryDocumentUpdateDto libraryDocumentUpdateDto, @MappingTarget LibraryDocument libraryDocument);
}
package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.FileStorageCreateDto;
import com.yildiz.terapinisec.dto.FileStorageResponseDto;
import com.yildiz.terapinisec.dto.FileStorageUpdateDto;
import com.yildiz.terapinisec.model.FileStorage;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileStorageMapper {

    @Mapping(source = "documentUploaderId" , target = "documentUploader.id")
    FileStorage toFileStorage(FileStorageCreateDto fileStorageCreateDto);

    @Mapping(source = "documentUploader.username" , target = "documentUploaderUsername")
    FileStorageResponseDto toFileStorageResponseDto(FileStorage fileStorage);

    @IterableMapping(elementTargetType = FileStorageResponseDto.class)
    List<FileStorageResponseDto> toFileStorageResponseDtoList(List<FileStorage> fileStorages);

    void updateFileStorageFromDto(FileStorageUpdateDto fileStorageUpdateDto , @MappingTarget FileStorage filestorage);
}

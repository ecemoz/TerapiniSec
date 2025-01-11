package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.MeditationContentCreateDto;
import com.yildiz.terapinisec.dto.MeditationContentResponseDto;
import com.yildiz.terapinisec.dto.MeditationContentUpdateDto;
import com.yildiz.terapinisec.model.MeditationContent;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeditationContentMapper {

    public MeditationContent toMeditationContent(MeditationContentCreateDto createDto, User createdBy) {
        if (createDto == null || createdBy == null) {
            return null;
        }

        return MeditationContent.builder()
                .title(createDto.getTitle())
                .description(createDto.getDescription())
                .meditationSessionType(createDto.getMeditationSessionType())
                .contentUrl(createDto.getContentUrl())
                .isPublic(createDto.isPublic())
                .createdBy(createdBy)
                .build();
    }

    public MeditationContentResponseDto toMeditationContentResponseDto(MeditationContent meditationContent) {
        if (meditationContent == null) {
            return null;
        }

        return MeditationContentResponseDto.builder()
                .id(meditationContent.getId())
                .title(meditationContent.getTitle())
                .description(meditationContent.getDescription())
                .meditationSessionType(meditationContent.getMeditationSessionType())
                .contentUrl(meditationContent.getContentUrl())
                .isPublic(meditationContent.isPublic())
                .createdByUsername(meditationContent.getCreatedBy() != null ? meditationContent.getCreatedBy().getUserName() : null)
                .build();
    }

    public void updateMeditationContentFromDto(MeditationContentUpdateDto updateDto, MeditationContent meditationContent) {
        if (updateDto == null || meditationContent == null) {
            return;
        }

        meditationContent.setTitle(updateDto.getTitle());
        meditationContent.setDescription(updateDto.getDescription());
        meditationContent.setMeditationSessionType(updateDto.getMeditationSessionType());
        meditationContent.setContentUrl(updateDto.getContentUrl());
        meditationContent.setPublic(updateDto.isPublic());
    }

    public List<MeditationContentResponseDto> toMeditationContentResponseDtoList(List<MeditationContent> meditationContents) {
        if (meditationContents == null || meditationContents.isEmpty()) {
            return List.of();
        }

        return meditationContents.stream()
                .map(this::toMeditationContentResponseDto)
                .collect(Collectors.toList());
    }
}

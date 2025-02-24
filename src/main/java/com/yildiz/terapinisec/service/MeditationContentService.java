package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.MeditationContentCreateDto;
import com.yildiz.terapinisec.dto.MeditationContentResponseDto;
import com.yildiz.terapinisec.dto.MeditationContentUpdateDto;
import com.yildiz.terapinisec.mapper.MeditationContentMapper;
import com.yildiz.terapinisec.model.MeditationContent;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.MeditationContentRepository;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeditationContentService {
    private final MeditationContentRepository meditationContentRepository;
    private final MeditationContentMapper meditationContentMapper;
    private final UserRepository userRepository;

    public MeditationContentService(MeditationContentRepository meditationContentRepository,
                                    MeditationContentMapper meditationContentMapper,
                                    UserRepository userRepository) {
        this.meditationContentRepository = meditationContentRepository;
        this.meditationContentMapper = meditationContentMapper;
        this.userRepository = userRepository;
    }

    public List<MeditationContentResponseDto> getAllMeditationContent() {
       List<MeditationContent> contents = meditationContentRepository.findAll();
       return contents.stream()
               .map(meditationContentMapper::toMeditationContentResponseDto)
               .collect(Collectors.toList());
    }

    public MeditationContentResponseDto getMeditationContentById(Long id) {
       MeditationContent content = meditationContentRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("MeditationContent not found"));
       return meditationContentMapper.toMeditationContentResponseDto(content);
    }

    public MeditationContentResponseDto createMeditationContent(MeditationContentCreateDto meditationContentCreateDto) {
        User createdBy = userRepository.findById(meditationContentCreateDto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + meditationContentCreateDto.getCreatedById()));

        MeditationContent content = meditationContentMapper.toMeditationContent(meditationContentCreateDto, createdBy);
        MeditationContent savedContent = meditationContentRepository.save(content);
        return meditationContentMapper.toMeditationContentResponseDto(savedContent);
    }

    public MeditationContentResponseDto updateMeditationContent(Long id, MeditationContentUpdateDto meditationContentUpdateDto) {
        MeditationContent existingContent = meditationContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MeditationContent not found"));
        meditationContentMapper.updateMeditationContentFromDto(meditationContentUpdateDto, existingContent);
        MeditationContent updatedContent = meditationContentRepository.save(existingContent);
        return meditationContentMapper.toMeditationContentResponseDto(updatedContent);
    }

    public void deleteMeditationContentById(Long id) {
        if (meditationContentRepository.existsById(id)) {
            meditationContentRepository.deleteById(id);
        } else {
            throw new RuntimeException("MeditationContent not found");
        }
    }

    public List<MeditationContentResponseDto>findByIsPublicTrue() {
        List<MeditationContent> contents = meditationContentRepository.findByIsPublicTrue();
        return contents.stream()
                .map(meditationContentMapper::toMeditationContentResponseDto)
                .collect(Collectors.toList());
    }

    public  List<MeditationContentResponseDto>findByType(MeditationSessionType type) {
        List<MeditationContent> contents = meditationContentRepository.findByMeditationSessionType(type);
        return contents.stream()
                .map(meditationContentMapper::toMeditationContentResponseDto)
                .collect(Collectors.toList());
    }

    public List<MeditationContentResponseDto>findByIsPublicFalse() {
        List<MeditationContent> contents = meditationContentRepository.findByIsPublicFalse();
        return contents.stream()
                .map(meditationContentMapper::toMeditationContentResponseDto)
                .collect(Collectors.toList());
    }

    public List<MeditationContentResponseDto>findByCreatedById(Long userId) {
        List<MeditationContent> contents = meditationContentRepository.findByCreatedById(userId);
        return contents.stream()
                .map(meditationContentMapper::toMeditationContentResponseDto)
                .collect(Collectors.toList());
    }
}
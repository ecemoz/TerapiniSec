package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.MeditationContent;
import com.yildiz.terapinisec.repository.MeditationContentRepository;
import com.yildiz.terapinisec.util.MeditationSessionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MeditationContentService {

    @Autowired
    private MeditationContentRepository meditationContentRepository;

    public List<MeditationContent> getAllMeditationContent() {
        return meditationContentRepository.findAll();
    }

    public Optional<MeditationContent > getMeditationContentById(Long id) {
        return meditationContentRepository.findById(id);
    }

    public MeditationContent createMeditationContent(MeditationContent updatedMeditationContent) {
        updatedMeditationContent.setContentUrl(updatedMeditationContent.getContentUrl());
        updatedMeditationContent.setDescription(updatedMeditationContent.getDescription());
        updatedMeditationContent.setTitle(updatedMeditationContent.getTitle());
        updatedMeditationContent.setMeditationSessionType(updatedMeditationContent.getMeditationSessionType());
        updatedMeditationContent.setPublic(updatedMeditationContent.isPublic());
        return meditationContentRepository.save(updatedMeditationContent);
    }

    public MeditationContent updateMeditationContent(Long id, MeditationContent updatedMeditationContent) {
        return meditationContentRepository.findById(id)
                .map(meditationContent -> {
                    meditationContent.setContentUrl(updatedMeditationContent.getContentUrl());
                    meditationContent.setDescription(updatedMeditationContent.getDescription());
                    meditationContent.setTitle(updatedMeditationContent.getTitle());
                    meditationContent.setMeditationSessionType(updatedMeditationContent.getMeditationSessionType());
                    return meditationContentRepository.save(meditationContent);
                })
                .orElseThrow(()-> new RuntimeException("MeditationContent not found"));
    }

    public void deleteMeditationContentById(Long id) {
        if (meditationContentRepository.existsById(id)) {
            meditationContentRepository.deleteById(id);
        } else {
            throw new RuntimeException("MeditationContent not found");
        }
    }

    public List<MeditationContent>findByIsPublicTrue() {
        return meditationContentRepository.findByIsPublicTrue();
    }

    public  List<MeditationContent>findByType(MeditationSessionType type) {
        return meditationContentRepository.findByType(type);
    }

    public List<MeditationContent>findByIsPublicFalse() {
        return meditationContentRepository.findByIsPublicFalse();
    }

    public List<MeditationContent>findByCreatedById(Long userId) {
        return meditationContentRepository.findByCreatedById(userId);
    }
}
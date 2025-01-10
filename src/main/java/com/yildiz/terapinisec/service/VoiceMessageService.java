package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.VoiceMessageCreateDto;
import com.yildiz.terapinisec.dto.VoiceMessageResponseDto;
import com.yildiz.terapinisec.mapper.VoiceMessageMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.model.VoiceMessage;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.repository.VoiceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoiceMessageService {

    @Autowired
    private VoiceMessageRepository voiceMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoiceMessageMapper voiceMessageMapper;

    public List<VoiceMessageResponseDto> getAllVoiceMessages() {
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findAll();
        return voiceMessages.stream()
                .map(voiceMessageMapper::toVoiceMessageResponseDto)
                .collect(Collectors.toList());
    }

    public VoiceMessageResponseDto getVoiceMessageById(Long id) {
        VoiceMessage voiceMessage = voiceMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VoiceMessage not found"));
        return voiceMessageMapper.toVoiceMessageResponseDto(voiceMessage);
    }

    public VoiceMessageResponseDto createVoiceMessage(VoiceMessageCreateDto voiceMessageCreateDto) {
        User speaker = userRepository.findById(voiceMessageCreateDto.getSpeakerId())
                .orElseThrow(() -> new RuntimeException("Speaker not found"));
        User listener = userRepository.findById(voiceMessageCreateDto.getListenerId())
                .orElseThrow(() -> new RuntimeException("Listener not found"));

        VoiceMessage voiceMessage = voiceMessageMapper.toVoiceMessage(voiceMessageCreateDto, speaker, listener);
        VoiceMessage savedVoiceMessage = voiceMessageRepository.save(voiceMessage);
        return voiceMessageMapper.toVoiceMessageResponseDto(savedVoiceMessage);
    }

    public void deleteVoiceMessage(Long id) {
        if (voiceMessageRepository.existsById(id)) {
            voiceMessageRepository.deleteById(id);
        } else {
            throw new RuntimeException("Voice Message not found.");
        }
    }

    public List<VoiceMessageResponseDto> findBySpeakerId(Long speakerId) {
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findBySpeakerId(speakerId);
        return voiceMessages.stream()
                .map(voiceMessageMapper::toVoiceMessageResponseDto)
                .collect(Collectors.toList());
    }

    public List<VoiceMessageResponseDto> findByListenerId(Long listenerId) {
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findByListenerId(listenerId);
        return voiceMessages.stream()
                .map(voiceMessageMapper::toVoiceMessageResponseDto)
                .collect(Collectors.toList());
    }

    public List<VoiceMessageResponseDto> findBySpeakerIdAndListenerId(Long speakerId, Long listenerId) {
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findBySpeakerIdAndListenerId(speakerId, listenerId);
        return voiceMessages.stream()
                .map(voiceMessageMapper::toVoiceMessageResponseDto)
                .collect(Collectors.toList());
    }

    public VoiceMessageResponseDto findByAudioUrl(String audioUrl) {
        VoiceMessage voiceMessage = voiceMessageRepository.findByAudioUrl(audioUrl);
        if (voiceMessage == null) {
            throw new RuntimeException("VoiceMessage not found with the given audio URL");
        }
        return voiceMessageMapper.toVoiceMessageResponseDto(voiceMessage);
    }
}
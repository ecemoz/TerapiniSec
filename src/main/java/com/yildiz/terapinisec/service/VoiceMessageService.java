package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.VoiceMessageCreateDto;
import com.yildiz.terapinisec.dto.VoiceMessageResponseDto;
import com.yildiz.terapinisec.mapper.AppointmentMapper;
import com.yildiz.terapinisec.mapper.VoiceMessageMapper;
import com.yildiz.terapinisec.model.VoiceMessage;
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
    private VoiceMessageMapper voiceMessageMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    public List<VoiceMessageResponseDto> getAllVoiceMessages() {
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findAll();
        return voiceMessages.stream()
                .map(voiceMessageMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public VoiceMessageResponseDto getVoiceMessageById(Long id) {
        VoiceMessage voiceMessage = voiceMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VoiceMessage not found"));
        return voiceMessageMapper.toResponseDto(voiceMessage);
    }

    public VoiceMessageResponseDto createVoiceMessage(VoiceMessageCreateDto voiceMessageCreateDto) {
        VoiceMessage voiceMessage = voiceMessageMapper.toEntity(voiceMessageCreateDto);
        voiceMessage = voiceMessageRepository.save(voiceMessage);
        return voiceMessageMapper.toResponseDto(voiceMessage);
    }

    public void deleteVoiceMessage(Long id) {
        if (voiceMessageRepository.existsById(id)) {
            voiceMessageRepository.deleteById(id);
        } else {
            throw new RuntimeException("Voice Message not found.");
        }
    }

    public List<VoiceMessageResponseDto>findBySpeakerId(Long senderId){
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findBySpeakerId(senderId);
        return voiceMessageMapper.toVoiceMessageResponseDtoList(voiceMessages);
    }

    public List<VoiceMessageResponseDto>findByListenerId(Long recipientId) {
        List<VoiceMessage> voiceMessages = voiceMessageRepository.findByListenerId(recipientId);
        return voiceMessageMapper.toVoiceMessageResponseDtoList(voiceMessages);
    }

    public List<VoiceMessageResponseDto>findBySpeakerIdAndListenerId(Long speakerId, Long listenerId) {
       List<VoiceMessage> voiceMessages = voiceMessageRepository.findBySpeakerIdAndListenerId(speakerId, listenerId);
       return voiceMessageMapper.toVoiceMessageResponseDtoList(voiceMessages);
    }

    public VoiceMessageResponseDto findByAudioUrl(String audioUrl) {
        VoiceMessage voiceMessages = voiceMessageRepository.findByAudioUrl(audioUrl);
        return voiceMessageMapper.toResponseDto(voiceMessages);
    }
}
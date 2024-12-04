package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.VoiceMessage;
import com.yildiz.terapinisec.repository.VoiceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoiceMessageService {

    @Autowired
    private VoiceMessageRepository voiceMessageRepository;

    public List<VoiceMessage> getAllVoiceMessages() {
        return voiceMessageRepository.findAll();
    }

    public Optional <VoiceMessage> getVoiceMessageById(Long id) {
        return voiceMessageRepository.findById(id);
    }

    public VoiceMessage createVoiceMessage(VoiceMessage voiceMessage) {
        voiceMessage.setTimeStamp(LocalDateTime.now());
        return voiceMessageRepository.save(voiceMessage);
    }

    public void deleteVoiceMessage(Long id) {
        if (voiceMessageRepository.existsById(id)) {
            voiceMessageRepository.deleteById(id);
        } else {
            throw new RuntimeException("Voice Message not found.");
        }
    }

    public List<VoiceMessage>findBySpeakerId(Long senderId){
        return voiceMessageRepository.findBySpeakerId(senderId);
    }

    public List<VoiceMessage>findByListenerId(Long recipientId) {
        return voiceMessageRepository.findByListenerId(recipientId);
    }

    public List<VoiceMessage>findBySpeakerIdAndListenerId(Long speakerId, Long listenerId) {
        return voiceMessageRepository.findBySpeakerIdAndListenerId(speakerId, listenerId);
    }

    public VoiceMessage findByAudioUrl(String audioUrl) {
        return voiceMessageRepository.findByAudioUrl(audioUrl);
    }
}
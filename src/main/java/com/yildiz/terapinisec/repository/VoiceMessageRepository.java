package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.VoiceMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VoiceMessageRepository extends JpaRepository<VoiceMessage, Long> {

    List<VoiceMessage>findBySpeakerId(Long senderId);
    List<VoiceMessage>findByListenerId(Long recipientId);
    List<VoiceMessage>findBySpeakerIdAndListenerId(Long speakerId, Long listenerId);
    VoiceMessage findByAudioUrl(String audioUrl);
}
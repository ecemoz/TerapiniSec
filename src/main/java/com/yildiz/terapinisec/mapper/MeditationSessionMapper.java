package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.MeditationSessionCreateDto;
import com.yildiz.terapinisec.dto.MeditationSessionResponseDto;
import com.yildiz.terapinisec.model.MeditationContent;
import com.yildiz.terapinisec.model.MeditationSession;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeditationSessionMapper {

    public MeditationSession toMeditationSession(MeditationSessionCreateDto createDto, User meditator, MeditationContent meditationContent) {
        if (createDto == null || meditator == null || meditationContent == null) {
            return null;
        }

        return MeditationSession.builder()
                .meditationSessionDuration(createDto.getMeditationSessionDuration())
                .meditationDateTime(createDto.getMeditationDateTime())
                .meditator(meditator)
                .meditationContent(meditationContent)
                .build();
    }

    public MeditationSessionResponseDto toMeditationSessionResponseDto(MeditationSession session) {
        if (session == null) {
            return null;
        }

        return MeditationSessionResponseDto.builder()
                .id(session.getId())
                .meditationSessionDuration(session.getMeditationSessionDuration())
                .meditationDateTime(session.getMeditationDateTime())
                .meditatorUsername(session.getMeditator() != null ? session.getMeditator().getUserName() : null)
                .meditationContentTitle(session.getMeditationContent() != null ? session.getMeditationContent().getTitle() : null)
                .build();
    }

    public List<MeditationSessionResponseDto> toMeditationSessionResponseDtoList(List<MeditationSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return List.of();
        }

        return sessions.stream()
                .map(this::toMeditationSessionResponseDto)
                .collect(Collectors.toList());
    }
}

package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.MoodLogCreateDto;
import com.yildiz.terapinisec.dto.MoodLogResponseDto;
import com.yildiz.terapinisec.dto.MoodLogUpdateDto;
import com.yildiz.terapinisec.model.MoodLog;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoodLogMapper {

    public MoodLog toMoodLog(MoodLogCreateDto createDto, User moodOwner) {
        if (createDto == null || moodOwner == null) {
            return null;
        }

        return MoodLog.builder()
                .userMoods(createDto.getUserMoods())
                .description(createDto.getDescription())
                .moodOwner(moodOwner)
                .build();
    }

    public MoodLogResponseDto toMoodLogResponseDto(MoodLog moodLog) {
        if (moodLog == null) {
            return null;
        }

        return MoodLogResponseDto.builder()
                .id(moodLog.getId())
                .usermoods(moodLog.getUserMoods())
                .description(moodLog.getDescription())
                .logDateTime(moodLog.getLogDateTime())
                .moodOwnerUsername(moodLog.getMoodOwner() != null ? moodLog.getMoodOwner().getUserName() : null)
                .build();
    }

    public void updateMoodLogFromDto(MoodLogUpdateDto updateDto, MoodLog moodLog) {
        if (updateDto == null || moodLog == null) {
            return;
        }

        moodLog.setUserMoods(updateDto.getUsermoods());
        moodLog.setDescription(updateDto.getDescription());
    }

    public List<MoodLogResponseDto> toMoodLogResponseDtoList(List<MoodLog> moodLogs) {
        if (moodLogs == null || moodLogs.isEmpty()) {
            return List.of();
        }

        return moodLogs.stream()
                .map(this::toMoodLogResponseDto)
                .collect(Collectors.toList());
    }
}

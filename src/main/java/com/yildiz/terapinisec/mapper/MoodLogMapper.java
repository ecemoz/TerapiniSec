package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.MoodLogCreateDto;
import com.yildiz.terapinisec.dto.MoodLogResponseDto;
import com.yildiz.terapinisec.dto.MoodLogUpdateDto;
import com.yildiz.terapinisec.model.MoodLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MoodLogMapper {

    @Mapping(source = "moodOwnerId", target = "moodOwner.id")
    MoodLog toMoodLog (MoodLogCreateDto moodLogCreateDto);

    @Mapping(source = "moodOwner.username" , target = "moodOwnerUsername")
    MoodLogResponseDto toMoodLogResponseDto(MoodLog moodLog);

    void updateMoodLogFromDto (MoodLogUpdateDto moodLogUpdateDto , @MappingTarget MoodLog moodLog);
}
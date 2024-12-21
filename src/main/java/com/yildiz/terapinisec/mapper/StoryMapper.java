package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.StoryCreateDto;
import com.yildiz.terapinisec.dto.StoryDetailedResponseDto;
import com.yildiz.terapinisec.dto.StoryResponseDto;
import com.yildiz.terapinisec.dto.StoryUpdateDto;
import com.yildiz.terapinisec.model.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryMapper {

    Story toStory(StoryCreateDto storyCreateDto);

    Story updateStory(StoryUpdateDto storyUpdateDto ,@MappingTarget Story story);

    StoryResponseDto toStoryResponseDto(Story story);

    @Mapping(source = "storyViews" , target = "storyViews")
    StoryDetailedResponseDto toStoryDetailedResponseDto(Story story);

    List<StoryResponseDto> toStoryResponseDtoList(List<Story> stories);
}
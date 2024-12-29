package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.StoryViewCreateDto;
import com.yildiz.terapinisec.dto.StoryViewDetailedDto;
import com.yildiz.terapinisec.dto.StoryViewResponseDto;
import com.yildiz.terapinisec.model.StoryView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryViewMapper {

    @Mapping(source = "storyId" , target = "story.id")
    @Mapping(source = "userId", target = "user.id")
    StoryView toStoryView(StoryViewCreateDto storyViewCreateDto);

    @Mapping(source = "story.id" , target = "storyId")
    @Mapping(source = "user.id"  , target = "userId")
    StoryViewResponseDto toStoryViewResponseDto(StoryView storyView);

    @Mapping(source = "story.description" , target = "storyDescription")
    @Mapping(source = "user.username" , target = "username")
    StoryViewDetailedDto toStoryViewDetailedDto (StoryView storyView);

    Page<StoryViewResponseDto> toStoryViewResponseDtoList(Page<StoryView> storyViews);

    List<StoryViewDetailedDto> toStoryViewDetailedDtoList(List<StoryView> storyViews);
}
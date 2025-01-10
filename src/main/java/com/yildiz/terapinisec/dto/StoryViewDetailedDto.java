package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class StoryViewDetailedDto {

   private Long id ;
   private LocalDateTime viewedAt;
   private String storyDescription;
   private String username;
}
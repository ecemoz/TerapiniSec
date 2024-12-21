package com.yildiz.terapinisec.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StoryViewDetailedDto {

   private Long id ;
   private LocalDateTime viewedAt;
   private String storyDescription;
   private String username;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.UserMoods;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MoodLogResponseDto {

    private Long id;
    private List<UserMoods> usermoods;
    private String description;
    private LocalDateTime logDateTime;
    private String moodOwnerUsername;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.UserMoods;
import lombok.Data;
import java.util.List;

@Data
public class MoodLogCreateDto {

    private List<UserMoods> userMoods;
    private String description;
    private Long moodOwnerId;
}
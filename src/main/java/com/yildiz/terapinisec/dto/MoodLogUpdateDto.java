package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.UserMoods;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MoodLogUpdateDto {

    private List<UserMoods> usermoods;
    private String description;
}
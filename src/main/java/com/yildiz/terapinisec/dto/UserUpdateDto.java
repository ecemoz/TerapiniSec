package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.Specialization;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserUpdateDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthday;
    private List<Specialization> specializations;
    private Integer yearsOfExperience;
    private List<LocalDateTime>availableTimes;
}
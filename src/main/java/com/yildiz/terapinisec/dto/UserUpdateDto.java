package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.Specialization;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserUpdateDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime birthday;
    private List <Specialization> specialization;
    private Integer yearsOfExperience;
    private List<LocalDateTime> availableTimes;
}
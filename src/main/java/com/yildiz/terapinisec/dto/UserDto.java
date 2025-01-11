package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isPremium;
    private LocalDateTime registrationDateTime;
    private LocalDateTime lastLoginDateTime;
    private UserRole userRole;
    private Specialization specialization;
    private Integer yearsOfExperience;
    private List<LocalDateTime> availableTimes;
}
package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isPremium;
    private LocalDateTime registrationDateTime;
    private LocalDateTime lastLoginDateTime;
    private UserRole userRole;
    private String specialization;
    private Integer yearsOfExperience;
}
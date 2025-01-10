package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.UserRole;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserCreateDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthday;
    private String phoneNumber;
    private String password;
    private UserRole userRole;
}
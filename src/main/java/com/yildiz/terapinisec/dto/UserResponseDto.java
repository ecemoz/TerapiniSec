package com.yildiz.terapinisec.dto;

import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Data
@Builder
public class UserResponseDto {

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
    private String specialization;
    private Integer yearsOfExperience;
    private LocalDateTime birthday;
    private List<LocalDateTime> availableTimes;
    private String userNameOrEmail;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }
}

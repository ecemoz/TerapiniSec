package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isPremium;
}
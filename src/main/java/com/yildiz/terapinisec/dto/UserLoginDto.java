package com.yildiz.terapinisec.dto;

import lombok.Data;

@Data
public class UserLoginDto {

    private String usernameOrEmail;
    private String password;
}
package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDto {

    private String userNameOrEmail;
    private String password;
}
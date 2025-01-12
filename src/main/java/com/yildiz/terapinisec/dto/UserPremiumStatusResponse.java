package com.yildiz.terapinisec.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPremiumStatusResponse {
    private boolean isPremium;
    private String message;
}

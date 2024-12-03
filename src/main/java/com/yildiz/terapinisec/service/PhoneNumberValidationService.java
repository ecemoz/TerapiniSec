package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.util.PhoneNumberUtil;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberValidationService {

    public String validatePhoneNumber(String phoneNumber) {
        if(!PhoneNumberUtil.isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        return phoneNumber;
    }
}
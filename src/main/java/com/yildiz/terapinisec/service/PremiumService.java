package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.UserResponseDto;
import com.yildiz.terapinisec.mapper.UserMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PremiumService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public PremiumService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDto upgradeToPremium(Long userId) {
        User premiumUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        premiumUser.setPremium(true);
        premiumUser.setPremiumStartDateTime(LocalDateTime.now());

        User updatedUser = userRepository.save(premiumUser);
        return userMapper.toUserResponseDto(updatedUser);
    }

    public UserResponseDto downgradeFromPremium(Long userId) {
        User downgradedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        downgradedUser.setPremium(false);
        downgradedUser.setPremiumEndDateTime(LocalDateTime.now());

        User updatedUser = userRepository.save(downgradedUser);
        return userMapper.toUserResponseDto(updatedUser);
    }

    public boolean isPremiumActive(Long userId) {
        return userRepository.findById(userId)
                .map(User::isPremium)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
}
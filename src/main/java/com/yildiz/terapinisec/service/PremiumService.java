package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PremiumService {

    @Autowired
    private UserRepository userRepository;

    public PremiumService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User upgradeToPremium(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setPremium(true);
                    user.setPremiumStartDateTime(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    public User downgradeFromPremium(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setPremium(false);
                    user.setPremiumEndDateTime(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    public boolean isPremiumActive(Long userId) {
        return userRepository.findById(userId)
                .map(User::isPremium)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }
}
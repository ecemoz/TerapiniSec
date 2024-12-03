package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneNumberValidationService phoneNumberValidationService;

    @Autowired
    private PremiumService premiumService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User updateUserPhoneNumber(Long userId, String phoneNumber) {
        return userRepository.findById(userId)
                        .map(user-> {
                            phoneNumberValidationService.validatePhoneNumber(phoneNumber);
                            return userRepository.save(user);
                        })
                .orElseThrow(()->new RuntimeException("User not found"));

    }

    public User makeUserPremium(Long userId) {
        return premiumService.upgradeToPremium(userId);
    }

    public User removeUserPremium(Long userId) {
        return premiumService.downgradeFromPremium(userId);
    }

    public void activePremium(Long userId) {
        if (premiumService.isPremiumActive(userId)) {
            System.out.println("You are premium member");
        } else {
            System.out.println("You are not premium member");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhoneNumber(phoneNumberValidationService.validatePhoneNumber(user.getPhoneNumber()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateBasicFields(existingUser, updatedUser);

                    if (existingUser.getUserRole().equals(UserRole.PSYCHOLOGIST)) {
                        updatePsychologistFields(existingUser, updatedUser);
                    } else if (existingUser.getUserRole().equals(UserRole.ADMIN)) {
                        updateAdminFields(existingUser, updatedUser);
                    }
                    return userRepository.save(existingUser);

                })
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    private void updateBasicFields(User existingUser, User updatedUser) {
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setBirthday(updatedUser.getBirthday());
    }

    private void updatePsychologistFields(User existingUser, User updatedUser) {
        if (updatedUser.getSpecializations() != null) {
            existingUser.setSpecializations(updatedUser.getSpecializations());
        }

        if (updatedUser.getYearsOfExperience() != null) {
            existingUser.setYearsOfExperience(updatedUser.getYearsOfExperience());
        }

        if (updatedUser.getAvailableTimes() != null) {
            existingUser.setAvailableTimes(updatedUser.getAvailableTimes());
        }
    }

    private void updateAdminFields(User existingUser, User updatedUser) {
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public List<User>findByRole (UserRole role) {
        return userRepository.findByRole(role);
    }

    public List<User>findByLastLoginDateTimeBefore(LocalDateTime dateTime) {
        return userRepository.findByLastLoginDateTimeBefore(dateTime);
    }

    public User findByLastLoginDateTimeAfter(LocalDateTime dateTime) {
        return userRepository.findByLastLoginDateTimeAfter(dateTime);
    }

    public List<User>findByIsPremiumTrue(){
        return userRepository.findByIsPremiumTrue();
    }

    public List<User>findByIsPremiumFalse(){
        return userRepository.findByIsPremiumFalse();
    }

    public List<User>findBySpecializationContains(Specialization specialization) {
        return userRepository.findBySpecializationContains(specialization);
    }

    public List<User>findByYearsOfExperienceGreaterThan(Integer years) {
        return userRepository.findByYearsOfExperienceGreaterThan(years);
    }
}
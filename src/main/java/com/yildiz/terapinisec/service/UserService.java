package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.mapper.UserMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    public UserResponseDto updateUserPhoneNumber(Long userId, String phoneNumber) {
        return userRepository.findById(userId)
                .map(user -> {
                    phoneNumberValidationService.validatePhoneNumber(phoneNumber);
                    user.setPhoneNumber(phoneNumber);
                    User updatedUser = userRepository.save(user);
                    return userMapper.toUserResponseDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDto makeUserPremium(Long userId) {
        return premiumService.upgradeToPremium(userId);
    }

    public UserResponseDto removeUserPremium(Long userId) {
        return premiumService.downgradeFromPremium(userId);
    }

    public UserPremiumStatusResponse activePremium(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isPremium = user.isPremium();

        String message = isPremium
                ? "You are a premium member."
                : "You are not a premium member.";

        return UserPremiumStatusResponse.builder()
                .isPremium(isPremium)
                .message(message)
                .build();
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserResponseDtoList(users);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        User user = userMapper.toUser(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setPhoneNumber(phoneNumberValidationService.validatePhoneNumber(userCreateDto.getPhoneNumber()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (existingUser.getUserRole() == UserRole.USER) {
                        existingUser.setSpecializations(null);
                        existingUser.setYearsOfExperience(null);
                        existingUser.setAvailableTimes(null);

                        if (userUpdateDto.getSpecialization() != null ||
                                userUpdateDto.getYearsOfExperience() != null ||
                                userUpdateDto.getAvailableTimes() != null) {
                            throw new IllegalArgumentException("USER role cannot update psychologist-specific fields");
                        }
                    }

                    userMapper.updateUserFromDto(userUpdateDto, existingUser);

                    if (existingUser.getUserRole() == UserRole.PSYCHOLOGIST) {
                        updatePsychologistFields(existingUser, userUpdateDto);
                    }

                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toUserResponseDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



    private void updatePsychologistFields(User existingUser, UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getSpecialization() != null) {
            existingUser.setSpecializations(userUpdateDto.getSpecialization());
        }
        if (userUpdateDto.getYearsOfExperience() != null) {
            existingUser.setYearsOfExperience(userUpdateDto.getYearsOfExperience());
        }
        if (userUpdateDto.getAvailableTimes() != null) {
            existingUser.setAvailableTimes(userUpdateDto.getAvailableTimes());
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUserName(username);
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto findByFirstNameAndLastName(String firstName, String lastName) {
        User user = userRepository.findByFirstNameAndLastName(firstName, lastName);
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto findByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return userMapper.toUserResponseDto(user);
    }

    public List<UserResponseDto> findByRole(UserRole role) {
        List<User> users = userRepository.findByUserRole(role);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByLastLoginDateTimeBefore(LocalDateTime dateTime) {
        List<User> users = userRepository.findByLastLoginDateTimeBefore(dateTime);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByLastLoginDateTimeAfter(LocalDateTime dateTime) {
        List<User> users = userRepository.findByLastLoginDateTimeAfter(dateTime);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByIsPremiumTrue() {
        List<User> users = userRepository.findByIsPremiumTrue();
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByIsPremiumFalse() {
        List<User> users = userRepository.findByIsPremiumFalse();
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findBySpecializationContains(Specialization specialization) {
        List<User> users = userRepository.findBySpecializationsContaining(specialization);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByYearsOfExperienceGreaterThan(Integer years) {
        List<User> users = userRepository.findByYearsOfExperienceGreaterThan(years);
        return userMapper.toUserResponseDtoList(users);
    }

    public boolean authenticate(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserNameOrEmail(userLoginDto.getUserNameOrEmail(),userLoginDto.getUserNameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            user.setLastLoginDateTime(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
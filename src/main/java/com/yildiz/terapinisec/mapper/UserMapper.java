package com.yildiz.terapinisec.mapper;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.model.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUser(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            return null;
        }
        return User.builder()
                .username(userCreateDto.getUsername())
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .email(userCreateDto.getEmail())
                .birthday(userCreateDto.getBirthday())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .password(userCreateDto.getPassword())
                .userRole(userCreateDto.getUserRole())
                .build();
    }

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isPremium(user.isPremium())
                .registrationDateTime(user.getRegistrationDateTime())
                .lastLoginDateTime(user.getLastLoginDateTime())
                .userRole(user.getUserRole())
                .specialization(user.getSpecializations() != null && !user.getSpecializations().isEmpty()
                        ? user.getSpecializations().get(0) : null)
                .yearsOfExperience(user.getYearsOfExperience())
                .availableTimes(user.getAvailableTimes())
                .build();
    }

    public UserResponseDto toUserResponseDto(User user) {
        if (user == null) {
            return null;
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isPremium(user.isPremium())
                .registrationDateTime(user.getRegistrationDateTime())
                .lastLoginDateTime(user.getLastLoginDateTime())
                .userRole(user.getUserRole())
                .specialization(user.getSpecializations() != null && !user.getSpecializations().isEmpty()
                        ? user.getSpecializations().get(0).name() : null)
                .yearsOfExperience(user.getYearsOfExperience())
                .build();
    }

    public void updateUserFromDto(UserUpdateDto userUpdateDto, User user) {
        if (userUpdateDto == null || user == null) {
            return;
        }

        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }
        if (userUpdateDto.getBirthday() != null) {
            user.setBirthday(userUpdateDto.getBirthday());
        }
        if (userUpdateDto.getSpecialization() != null) {
            user.setSpecializations(userUpdateDto.getSpecialization());
        }
        if (userUpdateDto.getYearsOfExperience() != null) {
            user.setYearsOfExperience(userUpdateDto.getYearsOfExperience());
        }
        if (userUpdateDto.getAvailableTimes() != null) {
            user.setAvailableTimes(userUpdateDto.getAvailableTimes());
        }
    }

    public List<UserResponseDto> toUserResponseDtoList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(this::toUserResponseDto)
                .collect(Collectors.toList());
    }
}
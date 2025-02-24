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
                .userName(userCreateDto.getUserName())
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .email(userCreateDto.getEmail())
                .birthday(userCreateDto.getBirthday())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .password(userCreateDto.getPassword())
                .userRole(userCreateDto.getUserRole())
                .build();
    }

    // UserResponseDto -> User dönüşümü
    public User toUser(UserResponseDto userResponseDto) {
        if (userResponseDto == null) {
            return null;
        }

        return User.builder()
                .id(userResponseDto.getId())
                .userName(userResponseDto.getUserName())
                .firstName(userResponseDto.getFirstName())
                .lastName(userResponseDto.getLastName())
                .email(userResponseDto.getEmail())
                .phoneNumber(userResponseDto.getPhoneNumber())
                .birthday(userResponseDto.getBirthday())
                .userRole(userResponseDto.getUserRole())
                .isPremium(userResponseDto.isPremium())
                .yearsOfExperience(userResponseDto.getYearsOfExperience())
                .availableTimes(userResponseDto.getAvailableTimes())
                .build();
    }

    // User -> UserDto dönüşümü
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isPremium(user.isPremium())
                .registrationDateTime(user.getRegistrationDateTime())
                .lastLoginDateTime(user.getLastLoginDateTime())
                .userRole(user.getUserRole())
                .specialization(user.getSpecializations() != null && !user.getSpecializations().isEmpty()
                        ? user.getSpecializations().get(0)
                        : null)
                .yearsOfExperience(user.getYearsOfExperience())
                .availableTimes(user.getAvailableTimes())
                .build();
    }

    // User -> UserResponseDto dönüşümü
    public UserResponseDto toUserResponseDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .birthday(user.getBirthday())
                .isPremium(user.isPremium())
                .registrationDateTime(user.getRegistrationDateTime())
                .lastLoginDateTime(user.getLastLoginDateTime())
                .userRole(user.getUserRole())
                .specialization(user.getSpecializations() != null && !user.getSpecializations().isEmpty()
                        ? user.getSpecializations().get(0).name()
                        : null)
                .yearsOfExperience(user.getYearsOfExperience())
                .availableTimes(user.getAvailableTimes())
                .build();
    }

    // UserUpdateDto -> User güncellemesi
    public void updateUserFromDto(UserUpdateDto userUpdateDto, User user) {
        if (userUpdateDto == null || user == null) {
            return;
        }

        if (userUpdateDto.getUserName() != null) {
            user.setUserName(userUpdateDto.getUserName());
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

    public List<UserDto> toUserDtoList(List<User> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }

        return users.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }
}

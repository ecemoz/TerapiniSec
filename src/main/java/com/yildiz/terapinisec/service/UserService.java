package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.UserCreateDto;
import com.yildiz.terapinisec.dto.UserLoginDto;
import com.yildiz.terapinisec.dto.UserResponseDto;
import com.yildiz.terapinisec.dto.UserUpdateDto;
import com.yildiz.terapinisec.mapper.UserMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
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
                        .map(user-> {
                            phoneNumberValidationService.validatePhoneNumber(phoneNumber);
                            user.setPhoneNumber(phoneNumber);
                            User updatedUser=userRepository.save(user);
                            return userMapper.toUserResponseDto(updatedUser);
                        })
                .orElseThrow(()->new RuntimeException("User not found"));

    }

    public UserResponseDto makeUserPremium(Long userId) {
        User premiumUser = premiumService.upgradeToPremium(userId);
        return userMapper.toUserResponseDto(premiumUser);
    }

    public UserResponseDto removeUserPremium(Long userId) {
        User downgradedUser = premiumService.downgradeFromPremium(userId);
        return userMapper.toUserResponseDto(downgradedUser);
    }

    public void activePremium(Long userId) {
        if (premiumService.isPremiumActive(userId)) {
            System.out.println("You are premium member");
        } else {
            System.out.println("You are not premium member");
        }
    }

    public List<UserResponseDto> getAllUsers() {
        List<User>users = userRepository.findAll();
        return userMapper.toUserResponseDtoList(users);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        User user = userMapper.toUser(userCreateDto);

        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setPhoneNumber(phoneNumberValidationService.validatePhoneNumber(userCreateDto.getPhoneNumber()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }

    public User updateUser(Long id, UserUpdateDto userUpdateDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    userMapper.updateUserFromDto(userUpdateDto,existingUser);

                    if (existingUser.getUserRole().equals(UserRole.PSYCHOLOGIST)) {
                        updatePsychologistFields(existingUser, userUpdateDto);
                    } else if (existingUser.getUserRole().equals(UserRole.ADMIN)) {
                        updateAdminFields(existingUser, userUpdateDto);
                    }
                    return userRepository.save(existingUser);

                })
                .orElseThrow(() -> new RuntimeException("User not found."));
    }


    private void updatePsychologistFields(User existingUser, UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getSpecializations() != null) {
            existingUser.setSpecializations(userUpdateDto.getSpecializations());
        }

        if (userUpdateDto.getYearsOfExperience() != null) {
            existingUser.setYearsOfExperience(userUpdateDto.getYearsOfExperience());
        }

        if (userUpdateDto.getAvailableTimes() != null) {
            existingUser.setAvailableTimes(userUpdateDto.getAvailableTimes());
        }
    }

    private void updateAdminFields(User existingUser, UserUpdateDto userUpdateDto) {
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

    public List<UserResponseDto>findByRole (UserRole role) {
        List<User>users = userRepository.findByRole(role);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto>findByLastLoginDateTimeBefore(LocalDateTime dateTime) {
        List<User> users = userRepository.findByLastLoginDateTimeBefore(dateTime);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByLastLoginDateTimeAfter(LocalDateTime dateTime) {
        List<User> users = userRepository.findByLastLoginDateTimeAfter(dateTime);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto>findByIsPremiumTrue(){
        List<User> users = userRepository.findByIsPremiumTrue();
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto>findByIsPremiumFalse(){
        List<User> users = userRepository.findByIsPremiumFalse();
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto>findBySpecializationContains(Specialization specialization) {
        List<User> users = userRepository.findBySpecializationContains(specialization);
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto>findByYearsOfExperienceGreaterThan(Integer years) {
        List<User> users = userRepository.findByYearsOfExperienceGreaterThan(years);
        return userMapper.toUserResponseDtoList(users);
    }

    public boolean authenticate(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsernameOrEmail(userLoginDto.getUsernameOrEmail());
        if (user != null && passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return true;
        }
        return false;
    }
}
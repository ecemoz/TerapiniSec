package com.yildiz.terapinisec.service;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.mapper.UserMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.repository.UserRepository;
import com.yildiz.terapinisec.util.JwtUtil;
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

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponseDto updateUserPhoneNumber(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        phoneNumberValidationService.validatePhoneNumber(phoneNumber);
        user.setPhoneNumber(phoneNumber);
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(updatedUser);
    }

    public UserResponseDto makeUserPremium(Long userId) {
        return premiumService.upgradeToPremium(userId);
    }

    public UserResponseDto removeUserPremium(Long userId) {
        return premiumService.downgradeFromPremium(userId);
    }

    public UserPremiumStatusResponse activePremium(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

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
        if (users.isEmpty()) {
            throw new RuntimeException("No users found in the system.");
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        if (userRepository.findByUserName(userCreateDto.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.findByEmail(userCreateDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = userMapper.toUser(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setPhoneNumber(phoneNumberValidationService.validatePhoneNumber(userCreateDto.getPhoneNumber()));
        User savedUser = userRepository.save(user);

        return userMapper.toUserResponseDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));

        if (existingUser.getUserRole() == UserRole.USER) {
            existingUser.setSpecializations(null);
            existingUser.setYearsOfExperience(null);
            existingUser.setAvailableTimes(null);

            if (userUpdateDto.getSpecialization() != null ||
                    userUpdateDto.getYearsOfExperience() != null ||
                    userUpdateDto.getAvailableTimes() != null) {
                throw new IllegalArgumentException("USER role cannot update psychologist-specific fields.");
            }
        }

        userMapper.updateUserFromDto(userUpdateDto, existingUser);

        if (existingUser.getUserRole() == UserRole.PSYCHOLOGIST) {
            updatePsychologistFields(existingUser, userUpdateDto);
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toUserResponseDto(updatedUser);
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
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto findByFirstNameAndLastName(String firstName, String lastName) {
        User user = userRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with first name: " + firstName + " and last name: " + lastName));
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto findByUserNameOrEmail(String userNameOrEmail) {
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + userNameOrEmail));
        return userMapper.toUserResponseDto(user);
    }


    public UserResponseDto findByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with phone number: " + phoneNumber);
        }
        return userMapper.toUserResponseDto(user);
    }

    public List<UserResponseDto> findByRole(UserRole role) {
        List<User> users = userRepository.findByUserRole(role);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found with role: " + role);
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByLastLoginDateTimeBefore(LocalDateTime dateTime) {
        List<User> users = userRepository.findByLastLoginDateTimeBefore(dateTime);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found who last logged in before: " + dateTime);
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByLastLoginDateTimeAfter(LocalDateTime dateTime) {
        List<User> users = userRepository.findByLastLoginDateTimeAfter(dateTime);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found who last logged in after: " + dateTime);
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByIsPremiumTrue() {
        List<User> users = userRepository.findByIsPremiumTrue();
        if (users.isEmpty()) {
            throw new RuntimeException("No premium users found.");
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByIsPremiumFalse() {
        List<User> users = userRepository.findByIsPremiumFalse();
        if (users.isEmpty()) {
            throw new RuntimeException("No non-premium users found.");
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findBySpecializationContains(Specialization specialization) {
        List<User> users = userRepository.findBySpecializationsContaining(specialization);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found with specialization: " + specialization);
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public List<UserResponseDto> findByYearsOfExperienceGreaterThan(Integer years) {
        List<User> users = userRepository.findByYearsOfExperienceGreaterThan(years);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found with more than " + years + " years of experience.");
        }
        return userMapper.toUserResponseDtoList(users);
    }

    public String authenticate(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserNameOrEmail(userLoginDto.getUserNameOrEmail(), userLoginDto.getUserNameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + userLoginDto.getUserNameOrEmail()));

        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            user.setLastLoginDateTime(LocalDateTime.now());
            userRepository.save(user);

            UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
            return jwtUtil.generateToken(userResponseDto);
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public boolean validateToken(String token) {
        String usernameOrEmail = jwtUtil.extractUsernameOrEmail(token);
        User user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
        return jwtUtil.validateToken(token, userResponseDto);
    }

    public UserResponseDto registerNewUser(UserCreateDto userCreateDto) {

        if (userRepository.findByUserName(userCreateDto.getUserName()) != null) {
                throw new IllegalArgumentException("Username already exists.");
            }
        if (userRepository.findByEmail(userCreateDto.getEmail()) != null) {
                throw new IllegalArgumentException("Email already exists.");
            }

        User newUser = userMapper.toUser(userCreateDto);
        newUser.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        String validPhone = phoneNumberValidationService.validatePhoneNumber(userCreateDto.getPhoneNumber());
        newUser.setPhoneNumber(validPhone);
        newUser.setUserRole(UserRole.USER);
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserResponseDto(savedUser);
        }
}
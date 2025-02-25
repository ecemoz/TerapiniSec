package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.service.UserService;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        try {
            List<UserResponseDto> users = userService.getAllUsers();
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Users retrieved successfully.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve users: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
        try {
            UserResponseDto user = userService.getUserById(id);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User retrieved successfully.", user);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserResponseDto createdUser = userService.createUser(userCreateDto);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User created successfully.", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to create user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/update-phone")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUserPhoneNumber(@PathVariable Long id, @RequestParam String phoneNumber) {
        try {
            UserResponseDto updatedUser = userService.updateUserPhoneNumber(id, phoneNumber);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User phone number updated successfully.", updatedUser);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to update phone number: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to update phone number: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/validate-token")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestParam String token) {
        try {
            boolean isValid = userService.validateToken(token);
            return ResponseEntity.ok(new ApiResponse<>(true, "Token validation result.", isValid));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid token: " + ex.getMessage(), false));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        try {
            UserResponseDto updatedUser = userService.updateUser(id, userUpdateDto);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User updated successfully.", updatedUser);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Update failed: " + ex.getMessage(), null));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Update failed: " + ex.getMessage(), null));
        }
    }

    @PutMapping("/{id}/premium")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<ApiResponse<UserResponseDto>> makeUserPremium(@PathVariable Long id) {
        try {
            UserResponseDto premiumUser = userService.makeUserPremium(id);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User upgraded to premium successfully.", premiumUser);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to upgrade to premium: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to upgrade to premium: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/premium/remove")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<ApiResponse<UserResponseDto>> removeUserPremium(@PathVariable Long id) {
        try {
            UserResponseDto nonPremiumUser = userService.removeUserPremium(id);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User premium status removed successfully.", nonPremiumUser);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to remove premium: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to remove premium: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}/premium/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserPremiumStatusResponse>> checkPremiumStatus(@PathVariable Long id) {
        try {
            UserPremiumStatusResponse status = userService.activePremium(id);
            ApiResponse<UserPremiumStatusResponse> response = new ApiResponse<>(true, "Premium status retrieved successfully.", status);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserPremiumStatusResponse> response = new ApiResponse<>(false, "Failed to get premium status: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserPremiumStatusResponse> response = new ApiResponse<>(false, "Failed to get premium status: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#id)")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            ApiResponse<Void> response = new ApiResponse<>(true, "User deleted successfully.", null);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Failed to delete user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Failed to delete user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findByRole(@PathVariable UserRole role) {
        try {
            List<UserResponseDto> users = userService.findByRole(role);
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Users retrieved by role successfully.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve users by role: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/last-login/before")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findByLastLoginBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        try {
            List<UserResponseDto> users = userService.findByLastLoginDateTimeBefore(dateTime);
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Users retrieved who last logged in before the specified date.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve users: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/last-login/after")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findByLastLoginAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        try {
            List<UserResponseDto> users = userService.findByLastLoginDateTimeAfter(dateTime);
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Users retrieved who last logged in after the specified date.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve users: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/premium/true")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findByPremiumTrue() {
        try {
            List<UserResponseDto> users = userService.findByIsPremiumTrue();
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Premium users retrieved successfully.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve premium users: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/premium/false")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findByPremiumFalse() {
        try {
            List<UserResponseDto> users = userService.findByIsPremiumFalse();
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Non-premium users retrieved successfully.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve non-premium users: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/specialization")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findBySpecializationContains(
            @RequestParam Specialization specialization) {
        try {
            List<UserResponseDto> users = userService.findBySpecializationContains(specialization);
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Users with the specified specialization retrieved successfully.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve users by specialization: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/experience/greater-than")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findByExperienceGreaterThan(
            @RequestParam Integer years) {
        try {
            List<UserResponseDto> users = userService.findByYearsOfExperienceGreaterThan(years);
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(true, "Users with more than " + years + " years of experience retrieved successfully.", users);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ApiResponse<List<UserResponseDto>> response = new ApiResponse<>(false, "Failed to retrieve users: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/authenticate")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<String>> authenticate(@RequestBody UserLoginDto userLoginDto) {
        try {
            String token = userService.authenticate(userLoginDto);

            ApiResponse<String> response = new ApiResponse<>(true, "Authentication successful.", token);
            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException ex) {
            ApiResponse<String> response = new ApiResponse<>(false, "Authentication failed: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (IllegalArgumentException ex) {
            ApiResponse<String> response = new ApiResponse<>(false, "Authentication failed: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        } catch (RuntimeException ex) {
            ApiResponse<String> response = new ApiResponse<>(false, "Authentication failed: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/username")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<UserResponseDto>> findByUsername(@RequestParam String username) {
        try {
            UserResponseDto user = userService.findByUsername(username);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User retrieved successfully by username.", user);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by username: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by username: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/firstname-lastname")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST','USER')")
    public ResponseEntity<ApiResponse<UserResponseDto>> findByFirstnameAndLastname(
            @RequestParam String firstName, @RequestParam String lastName) {
        try {
            UserResponseDto user = userService.findByFirstNameAndLastName(firstName, lastName);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User retrieved successfully by first and last name.", user);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by first and last name: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by first and last name: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/email")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST')")
    public ResponseEntity<ApiResponse<UserResponseDto>> findByEmail(@RequestParam String email) {
        try {
            UserResponseDto user = userService.findByEmail(email);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User retrieved successfully by email.", user);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by email: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by email: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/phone-number")
    @PreAuthorize("hasAnyRole('ADMIN','PSYCHOLOGIST')")
    public ResponseEntity<ApiResponse<UserResponseDto>> findByPhoneNumber(@RequestParam String phoneNumber) {
        try {
            UserResponseDto user = userService.findByPhoneNumber(phoneNumber);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User retrieved successfully by phone number.", user);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by phone number: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to retrieve user by phone number: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserResponseDto createdUser = userService.registerNewUser(userCreateDto);

            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User registered successfully.", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to register user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to register user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.service.UserService;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public ApiResponse() {}

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

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
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserResponseDto createdUser = userService.createUser(userCreateDto);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User created successfully.", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to create user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Failed to create user: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}/update-phone")
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        try {
            UserResponseDto updatedUser = userService.updateUser(id, userUpdateDto);
            ApiResponse<UserResponseDto> response = new ApiResponse<>(true, "User updated successfully.", updatedUser);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Update failed: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (UsernameNotFoundException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Update failed: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<UserResponseDto> response = new ApiResponse<>(false, "Update failed: " + ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}/premium")
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
    public ResponseEntity<ApiResponse<Boolean>> authenticate(@RequestBody UserLoginDto userLoginDto) {
        try {
            boolean isAuthenticated = userService.authenticate(userLoginDto);
            if (isAuthenticated) {
                ApiResponse<Boolean> response = new ApiResponse<>(true, "Authentication successful.", true);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Boolean> response = new ApiResponse<>(false, "Authentication failed. Invalid credentials.", false);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (UsernameNotFoundException ex) {
            ApiResponse<Boolean> response = new ApiResponse<>(false, "Authentication failed: " + ex.getMessage(), false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException ex) {
            ApiResponse<Boolean> response = new ApiResponse<>(false, "Authentication failed: " + ex.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/username")
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
}

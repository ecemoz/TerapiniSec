package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.*;
import com.yildiz.terapinisec.service.UserService;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userService.createUser(userCreateDto));
    }

    @PutMapping("/{id}/update-phone")
    public ResponseEntity<UserResponseDto> updateUserPhoneNumber(@PathVariable Long id, @RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.updateUserPhoneNumber(id, phoneNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDto));
    }

    @PutMapping("/{id}/premium")
    public ResponseEntity<UserResponseDto> makeUserPremium(@PathVariable Long id) {
        return ResponseEntity.ok(userService.makeUserPremium(id));
    }

    @PutMapping("/{id}/premium/remove")
    public ResponseEntity<UserResponseDto> removeUserPremium(@PathVariable Long id) {
        return ResponseEntity.ok(userService.removeUserPremium(id));
    }

    @GetMapping("/{id}/premium/status")
    public ResponseEntity<UserPremiumStatusResponse> checkPremiumStatus(@PathVariable Long id) {
        UserPremiumStatusResponse response = userService.activePremium(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> findByRole(@PathVariable UserRole role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

    @GetMapping("/last-login/before")
    public ResponseEntity<List<UserResponseDto>> findByLastLoginBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return ResponseEntity.ok(userService.findByLastLoginDateTimeBefore(dateTime));
    }

    @GetMapping("/last-login/after")
    public ResponseEntity<List<UserResponseDto>> findByLastLoginAfter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return ResponseEntity.ok(userService.findByLastLoginDateTimeAfter(dateTime));
    }

    @GetMapping("/premium/true")
    public ResponseEntity<List<UserResponseDto>> findByPremiumTrue() {
        return ResponseEntity.ok(userService.findByIsPremiumTrue());
    }

    @GetMapping("/premium/false")
    public ResponseEntity<List<UserResponseDto>> findByPremiumFalse() {
        return ResponseEntity.ok(userService.findByIsPremiumFalse());
    }

    @GetMapping("/specialization")
    public ResponseEntity<List<UserResponseDto>> findBySpecializationContains(@RequestParam Specialization specialization) {
        return ResponseEntity.ok(userService.findBySpecializationContains(specialization));
    }

    @GetMapping("/experience/greater-than")
    public ResponseEntity<List<UserResponseDto>> findByExperienceGreaterThan(@RequestParam Integer years) {
        return ResponseEntity.ok(userService.findByYearsOfExperienceGreaterThan(years));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(userService.authenticate(userLoginDto));
    }

    @GetMapping("/username")
    public ResponseEntity<UserResponseDto> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/firstname-lastname")
    public ResponseEntity<UserResponseDto> findByFirstnameAndLastname(@RequestParam String firstname, @RequestParam String lastname) {
        return ResponseEntity.ok(userService.findByFirstNameAndLastName(firstname, lastname));
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/phone-number")
    public ResponseEntity<UserResponseDto> findByPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(userService.findByPhoneNumber(phoneNumber));
    }
}

package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.GoalCreateDto;
import com.yildiz.terapinisec.dto.GoalResponseDto;
import com.yildiz.terapinisec.dto.GoalUpdateDto;
import com.yildiz.terapinisec.service.GoalService;
import com.yildiz.terapinisec.util.GoalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GoalResponseDto>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isGoalOwner(#id)")
    public ResponseEntity<GoalResponseDto> getGoalById(@PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<GoalResponseDto> createGoal(@RequestBody GoalCreateDto goalCreateDto) {
        return ResponseEntity.ok(goalService.createGoal(goalCreateDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isGoalOwner(#id)")
    public ResponseEntity<GoalResponseDto> updateGoal(@PathVariable Long id, @RequestBody GoalUpdateDto goalUpdateDto) {
        return ResponseEntity.ok(goalService.updateGoal(id, goalUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isGoalOwner(#id)")
    public ResponseEntity<GoalResponseDto> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/goaltype")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GoalResponseDto>> getGoalByGoalType(@RequestParam GoalType goalType) {
        return ResponseEntity.ok(goalService.findByGoalType(goalType));
    }

    @GetMapping("/completed-true")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isGoalOwner(#id)")
    public ResponseEntity<List<GoalResponseDto>> getGoalCompletedTrue() {
        return ResponseEntity.ok(goalService.findByIsCompletedTrue());
    }

    @GetMapping("/completed-false")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isGoalOwner(#id)")
    public ResponseEntity<List<GoalResponseDto>> getGoalCompletedFalse() {
        return ResponseEntity.ok(goalService.findByIsCompletedFalse());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#userId)")
    public ResponseEntity<List<GoalResponseDto>> getGoalByGoalOwnerId(@PathVariable Long userId) {
        return ResponseEntity.ok(goalService.findByGoalOwnerId(userId));
    }

    @GetMapping("/{userId}/completed-false")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isSelf(#userId)")
    public ResponseEntity<List<GoalResponseDto>> getGoalCompletedFalseByGoalOwnerId(@PathVariable Long userId) {
        return ResponseEntity.ok(goalService.findByGoalOwnerIdAndIsCompletedFalse(userId));
    }
}
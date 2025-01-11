package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.AppointmentCreateDto;
import com.yildiz.terapinisec.dto.AppointmentResponseDto;
import com.yildiz.terapinisec.dto.AppointmentUpdateDto;
import com.yildiz.terapinisec.dto.UserResponseDto;
import com.yildiz.terapinisec.mapper.UserMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.service.AppointmentService;
import com.yildiz.terapinisec.service.UserService;
import com.yildiz.terapinisec.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto,
                                                                    @RequestParam Long clientId,
                                                                    @RequestParam Long therapistId) {
        return ResponseEntity.ok(appointmentService.createAppointment(appointmentCreateDto, clientId, therapistId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateDto appointmentUpdateDto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentUpdateDto));
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<AppointmentResponseDto> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestParam AppointmentStatus appointmentStatus,
            @RequestParam Long userId) {

        UserResponseDto userResponseDto = userService.getUserById(userId);
        User user = userMapper.toUser(userResponseDto);

        AppointmentResponseDto updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, appointmentStatus, user);
        return ResponseEntity.ok(updatedAppointment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-after")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDateAfter(@RequestParam LocalDateTime appointmentDate) {
        return ResponseEntity.ok(appointmentService.findByAppointmentDateAfter(appointmentDate));
    }

    @GetMapping("/date-between")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDateBetween(@RequestParam LocalDateTime appointmentDate1, @RequestParam LocalDateTime appointmentDate2) {
        return ResponseEntity.ok(appointmentService.findByAppointmentDateBetween(appointmentDate1, appointmentDate2));
    }

    @GetMapping("/status")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentStatus(@RequestParam AppointmentStatus appointmentStatus) {
        return ResponseEntity.ok(appointmentService.findByStatus(appointmentStatus));
    }

    @GetMapping("/date-between/status")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDateBetweenStatus(@RequestParam LocalDateTime appointmentDate1,
                                                                                        @RequestParam LocalDateTime appointmentDate2,
                                                                                        @RequestParam AppointmentStatus appointmentStatus) {
        return ResponseEntity.ok(appointmentService.findByAppointmentDateBetweenAndStatus(appointmentDate1, appointmentDate2, appointmentStatus));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.findAppointmentByUserId(userId));
    }
}

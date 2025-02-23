package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.dto.AppointmentCreateDto;
import com.yildiz.terapinisec.dto.AppointmentResponseDto;
import com.yildiz.terapinisec.dto.AppointmentUpdateDto;
import com.yildiz.terapinisec.dto.UserResponseDto;
import com.yildiz.terapinisec.mapper.UserMapper;
import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.security.SecurityService;
import com.yildiz.terapinisec.service.AppointmentService;
import com.yildiz.terapinisec.service.UserService;
import com.yildiz.terapinisec.util.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private SecurityService securityService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isAppointmentOwner(#id)")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto,
                                                                    @RequestParam Long clientId,
                                                                    @RequestParam Long therapistId) {
        return ResponseEntity.ok(appointmentService.createAppointment(appointmentCreateDto, clientId, therapistId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateDto appointmentUpdateDto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentUpdateDto));
    }

    @PutMapping("/{appointmentId}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isAppointmentOwner(#appointmentId)")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date-after")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDateAfter(@RequestParam LocalDateTime appointmentDate) {
        return ResponseEntity.ok(appointmentService.findByAppointmentDateAfter(appointmentDate));
    }

    @GetMapping("/date-between")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDateBetween(@RequestParam LocalDateTime appointmentDate1, @RequestParam LocalDateTime appointmentDate2) {
        return ResponseEntity.ok(appointmentService.findByAppointmentDateBetween(appointmentDate1, appointmentDate2));
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentStatus(@RequestParam AppointmentStatus appointmentStatus) {
        return ResponseEntity.ok(appointmentService.findByStatus(appointmentStatus));
    }

    @GetMapping("/date-between/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDateBetweenStatus(@RequestParam LocalDateTime appointmentDate1,
                                                                                        @RequestParam LocalDateTime appointmentDate2,
                                                                                        @RequestParam AppointmentStatus appointmentStatus) {
        return ResponseEntity.ok(appointmentService.findByAppointmentDateBetweenAndStatus(appointmentDate1, appointmentDate2, appointmentStatus));
    }

    @GetMapping("/client/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isAppointmentOwner(#userId)")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentByClientId(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.findAppointmentByClientId(userId));
    }

    @GetMapping("/therapist/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST') or @securityService.isAppointmentOwner(#userId)")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentByTherapistId(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.findAppointmentByTherapistId(userId));
    }
}

package com.yildiz.terapinisec.controller;

import com.yildiz.terapinisec.service.NotificationProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationProducerService notificationProducerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PSYCHOLOGIST')")
    public String sendNotification(@RequestParam String message) {
        String topic = "notifications";
        notificationProducerService.sendMessage(topic, message);
        return "Message sent to Kafka topic '" + topic + "': " + message;
    }
}
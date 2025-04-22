package com.yildiz.terapinisec.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    @KafkaListener(topics = "notification-events", groupId = "notification-group" )
    public void listenMessages(String message) {
        System.out.println("Received Messages: " + message);

        processMessage(message);
    }

    private void processMessage(String message) {
        System.out.println("Processing Message: " + message);
    }
}
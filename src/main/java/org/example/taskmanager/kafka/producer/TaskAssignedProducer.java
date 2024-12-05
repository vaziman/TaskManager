package org.example.taskmanager.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignedProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public TaskAssignedProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaskAssignedMessage(String topic, String key, String message) {
        try {
            // Отправка сообщения через KafkaTemplate
            kafkaTemplate.send(topic, key, message);
            System.out.println("Message sent to topic: " + topic);
        } catch (Exception e) {
            System.err.printf("Failed to send message: %s%n", e.getMessage());
        }
    }
}
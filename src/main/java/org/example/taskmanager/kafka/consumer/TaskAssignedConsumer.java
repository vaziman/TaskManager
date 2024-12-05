package org.example.taskmanager.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskAssignedConsumer {
    @KafkaListener(topics = "task-assigned-topic", groupId = "task-assigned-group")
    public void listen(String message) {
        System.out.println("Received task creation message: " + message);
        log.info("Received task creation message: " + message);

        // обработка события
    }
}


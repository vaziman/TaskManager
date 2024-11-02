package org.example.taskmanager.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.User;
import org.example.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> findByName(String name) {
        if (name != null) return taskRepository.findByName(name);
        return taskRepository.findAll();
    }

    public void createTask(Task task) {

        taskRepository.save(task);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public List<Task> getAllTasksForUser() {
        return taskRepository.findAll();
    }
}

package org.example.taskmanager.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.repositories.TaskRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<Task> findByName(String name) {
        if (name != null) return taskRepository.findByName(name);
        return taskRepository.findAll();
    }

    public void createTask(Task task, Long userId) {
        userRepository.findById(userId).ifPresent(task::setUser);
        taskRepository.save(task);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public List<Task> getAllTasksForUser() {
        return taskRepository.findAll();
    }
}

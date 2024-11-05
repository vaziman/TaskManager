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

    public List<Task> getTasksByUserId(Long id) {
        return taskRepository.findByUserId(id);
    }

    public void editTask(Task task) {
        taskRepository.save(task);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }


    public void save(Task task) {
        taskRepository.save(task);
    }
}

package org.example.taskmanager.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.models.entities.Task;
import org.example.taskmanager.models.entities.User;
import org.example.taskmanager.repositories.TaskRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
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

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }


    public List<Task> searchTasksByName(String name) {
        return taskRepository.findByName(name);
    }

    public void assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (task != null && user != null) {
            task.setUser(user);
            taskRepository.save(task);
        }
    }
}

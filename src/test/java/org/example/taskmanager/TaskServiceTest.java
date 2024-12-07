package org.example.taskmanager;


import org.example.taskmanager.models.entities.Task;
import org.example.taskmanager.models.entities.User;
import org.example.taskmanager.repositories.TaskRepository;
import org.example.taskmanager.repositories.UserRepository;
import org.example.taskmanager.services.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask_WithValidUser_SetsUserAndSavesTask() {
    Task task = new Task();
    Long userId = 1L;
    User user = new User();
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    taskService.createTask(task, userId);

    assertEquals(user, task.getUser());
    verify(taskRepository).save(task);
    }

    @Test
    void testCreateTask_WithInvalidUser_ReturnsFalse() {
    Task task = new Task();
    Long userId = 999L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    taskService.createTask(task, userId);
    verify(taskRepository).save(task);
    }

    @Test
    void testEditTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setName("Existing Task");
        existingTask.setDescription("Description");
        existingTask.setPriority(1);

        taskService.saveTask(existingTask);

        verify(taskRepository).save(existingTask);

        assertEquals("Existing Task", existingTask.getName());
        assertEquals("Description", existingTask.getDescription());
        assertEquals(1, existingTask.getPriority());
    }


    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setId(1L);
        taskService.deleteTask(task);
        verify(taskRepository).delete(task);
    }

    @Test
    void testGetAllTasksForUser() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setName("Task 1");
        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Task 2");

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> tasks = taskService.getAllTasksForUser();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());

        assertEquals("Task 1", tasks.get(0).getName());
        assertEquals("Task 2", tasks.get(1).getName());

        verify(taskRepository).findAll();
    }

    @Test
    void testSearchTasksByName() {

        Task task1 = new Task();
        task1.setName("Task 1");

        when(taskRepository.findByName(task1.getName())).thenReturn(List.of(task1));

        List<Task> tasks = taskService.searchTasksByName("Task 1");
        assertNotNull(tasks);
        assertEquals(1, tasks.size());

        assertEquals("Task 1", tasks.get(0).getName());

        verify(taskRepository).findByName(task1.getName());
    }


}

package org.example.taskmanager.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.taskmanager.kafka.producer.TaskAssignedProducer;
import org.example.taskmanager.models.TaskUpdateDTO;
import org.example.taskmanager.models.entities.Task;
import org.example.taskmanager.models.entities.User;
import org.example.taskmanager.services.TaskService;
import org.example.taskmanager.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final TaskAssignedProducer taskAssignedProducer;


    @GetMapping("/show-tasks")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userService.findUserByUserName(userDetails.getUsername());
            List<Task> tasks = taskService.getTasksByUserId(user.getId()); // set all params
            model.addAttribute("tasks", tasks);
        }
        return "home-page";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        model.addAttribute("task", new Task());
        return "/create-task";
    }

    @PostMapping("/create-task")
    public String createTask(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute Task task) {
        User user = (User) userDetails;
        taskService.createTask(task, user.getId());
        String messageForKafka = String.format("User Created Task");
        taskAssignedProducer.sendTaskAssignedMessage("task-assigned-topic", user.getUsername(), messageForKafka);
        return "redirect:/show-tasks";
    }

    @PostMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable Task id) {
        taskService.deleteTask(id);
        return "redirect:/show-tasks";
    }

    @GetMapping("/edit-task/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        Task task = taskService.findTaskById(id);
        List<User>  users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("task", task);
        return "/edit-task-page";
    }

    @PostMapping("/update-task")
    public String updateTask(@ModelAttribute TaskUpdateDTO task) {
        Long taskId = task.getId();
        Task taskToEdit = taskService.findTaskById(taskId);
        taskToEdit.setName(task.getName());
        taskToEdit.setDescription(task.getDescription());
        taskToEdit.setPriority(task.getPriority());

        if (task.getAssignedUser() != null) {
            User user = userService.findUserByUserName(task.getAssignedUser());
            taskToEdit.setAssignedUser(user.getUsername());
        }
        taskService.saveTask(taskToEdit);
        return "redirect:/show-tasks";
    }

    @GetMapping("/search-tasks")
    public String searchTasks(@RequestParam(value = "searchQuery", required = false) String name, Model model) {
        List<Task> tasks = taskService.searchTasksByName(name);
        if(name == null || name.isEmpty()) {
            model.addAttribute("tasks", taskService.getAllTasksForUser());
        }else {
            model.addAttribute("tasks", tasks);
        }
        model.addAttribute("searchQuery", name);
        return "home-page";
    }

}

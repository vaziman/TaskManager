package org.example.taskmanager.controllers;


import lombok.RequiredArgsConstructor;
import org.example.taskmanager.models.Task;
import org.example.taskmanager.models.User;
import org.example.taskmanager.services.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/show-tasks")
    public String home(@AuthenticationPrincipal UserDetails userDetails, User user, Model model) {
        if (userDetails != null) {
            List<Task> tasks = user.getTasksByUserName(userDetails.getUsername()); // set all params
            model.addAttribute("tasks", tasks);

        }
        return "home-page";
    }

    @GetMapping("/create-task")
    public String createTask(Task task, Model model) {
        taskService.createTask(task);
        return "/create-task";
    }

    @PostMapping("/create-task")
    public String createTask(Task task) {
        taskService.createTask(task);
        return "redirect:/show-tasks";
    }
}

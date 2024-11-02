package org.example.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.models.User;
import org.example.taskmanager.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Account with email" + user.getEmail() + " already exists");
            return "registration";
        }
        return "redirect:/login";
    }
}

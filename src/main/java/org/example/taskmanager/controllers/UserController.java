package org.example.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.example.taskmanager.models.entities.User;
import org.example.taskmanager.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registration(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email, User user, Model model) {

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Account with email" + user.getEmail() + " already exists");
            return "registration";
        }
        return "redirect:/login";
    }
}

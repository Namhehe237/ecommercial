package com.example.pttk.controller;
import com.example.pttk.model.User;
import com.example.pttk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RequestMapping("/")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    // Xử lý yêu cầu POST từ form đăng ký
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
        try {
            userService.save(user);  // Ensure that "user" is populated correctly
            model.addAttribute("message", "Signup successful! Please log in.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Signup failed: " + e.getMessage());
            return "redirect:/signup"; // Show signup page with error message
        }
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Optional<User> user = userService.findUserById(1L); // Dummy code
        model.addAttribute("user", user);
        return "redirect:/profile";
    }
}

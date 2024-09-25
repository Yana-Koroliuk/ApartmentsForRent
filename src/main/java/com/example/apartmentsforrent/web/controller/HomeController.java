package com.example.apartmentsforrent.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Object loggedInOwner = session.getAttribute("owner");
        if (loggedInOwner == null) {
            return "redirect:/owner/signin";
        }
        model.addAttribute("owner", loggedInOwner);
        return "home";
    }
}

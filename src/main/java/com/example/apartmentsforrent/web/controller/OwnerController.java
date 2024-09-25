package com.example.apartmentsforrent.web.controller;

import com.example.apartmentsforrent.persistence.model.Owner;
import com.example.apartmentsforrent.service.OwnerService;
import com.example.apartmentsforrent.web.converter.OwnerDtoConverter;
import com.example.apartmentsforrent.web.dto.OwnerDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/owner")
public class OwnerController {
    private OwnerService ownerService;
    private OwnerDtoConverter ownerDtoConverter;

    @Autowired
    public void setOwnerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Autowired
    public void setOwnerDtoConverter(OwnerDtoConverter ownerDtoConverter) {
        this.ownerDtoConverter = ownerDtoConverter;
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("owner", new OwnerDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute OwnerDto ownerDto, Model model) {
        if (ownerService.isEmailTaken(ownerDto.getEmail())) {
            model.addAttribute("error", "Email is already taken!");
            return "signup";
        }
        Owner owner = ownerDtoConverter.convertToOwner(ownerDto);
        ownerService.saveOwner(owner);
        model.addAttribute("owner", ownerDto);
        return "redirect:/owner/signin";
    }

    @GetMapping("/signin")
    public String showSigninForm(Model model) {
        model.addAttribute("ownerDto", new OwnerDto());
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute OwnerDto ownerDto, Model model, HttpSession session) {
        Owner owner = ownerService.getOwnerByEmail(ownerDto.getEmail());
        System.out.println(owner.getEmail());
        System.out.println(owner.getPasswordHash());

        if (owner == null || !owner.getPasswordHash().equals(ownerDto.getPasswordHash())) {
            model.addAttribute("error", "Invalid email or password!");
            return "signin";
        }

        session.setAttribute("owner", owner);
        model.addAttribute("owner", ownerDtoConverter.convertToOwnerDto(owner));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("owner");
        return "redirect:/";
    }
}
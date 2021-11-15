package com.example.faculty.controller;

import com.example.faculty.database.entity.User;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AdministratorController {


    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String getStudent(Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);
        return "/user/admin";
    }

    // TODO: 15.11.2021 test 
    @GetMapping("/admin/edit")
    public String showUpdateAdminForm(Model model){
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", admin);
        return "/user/admin/edit";
    }

    // TODO: 15.11.2021 not working update

    @PostMapping("/admin/update")
    public String updateAdmin(@Valid User user, BindingResult result, Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (result.hasErrors()) {
            return "redirect:/admin/edit";
        }
        userService.saveStudent(user);
        return "/user/teacher";
    }
}

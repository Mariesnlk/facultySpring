package com.example.faculty.controller;

import com.example.faculty.database.entity.User;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/teacher")
    public String getStudent(Model model) {
        User teacher = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("teacher", teacher);
        return "/user/teacher";
    }

    @GetMapping("/teacher/edit")
    public String teacher() {
        return "user/teacher/edit";
    }
}

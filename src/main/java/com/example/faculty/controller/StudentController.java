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
public class StudentController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/student")
    public String getStudent(Model model) {
        User student = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("student", student);
        return "/user/student";
    }

    @GetMapping("/student/edit")
    public String teacher() {
        return "user/student/edit";
    }

}

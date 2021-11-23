package com.example.faculty.controller;

import com.example.faculty.database.entity.User;
import com.example.faculty.models.dto.UserUpdate;
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
public class TeacherController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/teacher")
    public String getTeacher(Model model) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("teacher", userService.getUser(userId));
        return "/user/teacher";
    }

    @GetMapping("/teacher/update")
    public String showUpdateTeacherForm(Model model){
        User teacher = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", teacher);
        return "/user/teacher/edit";
    }

    @PostMapping("/teacher/update")
    public String updateTeacher(@Valid UserUpdate userUpdate, BindingResult result, Model model) {
        User teacher = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (result.hasErrors()) {
            return "/user/teacher/edit";
        }

        User updatedUser = User.builder()
                .id(teacher.getId())
                .firstName(userUpdate.getFirstName())
                .secondName(userUpdate.getSecondName())
                .lastName(userUpdate.getLastName())
                .email(userUpdate.getEmail())
                .password(teacher.getPassword())
                .roles(teacher.getRoles())
                .userRoleName(teacher.getUserRoleName())
                .build();

        userService.updateUser(updatedUser);
        return "redirect:/teacher";
    }
}

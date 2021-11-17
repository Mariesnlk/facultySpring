package com.example.faculty.controller;

import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.UserUpdate;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

    @GetMapping("/student/update")
    public String showUpdateForm(Model model) {
        User student = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", student);
        return "/user/student/edit";
    }

    // TODO: 17.11.2021 not working updated teacher? but it store in db need to login one more time
    @PostMapping("/student/update")
    public String updateStudent(@Valid UserUpdate userUpdate, BindingResult result, Model model) {
        User student = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User updatedUser = User.builder()
                .id(student.getId())
                .firstName(userUpdate.getFirstName())
                .secondName(userUpdate.getSecondName())
                .lastName(userUpdate.getLastName())
                .email(userUpdate.getEmail())
                .password(student.getPassword())
                .roles(student.getRoles())
                .userRoleName(student.getUserRoleName())
                .build();

//        if (result.hasErrors()) {
//            return "redirect:/student/edit";
//        }
        userService.updateUser(updatedUser);
        model.addAttribute("user", student);
        return "redirect:/student";
    }

    //<a th:href="@{/delete/{id}(id=${user.id})}">Delete</a>
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/index";
    }

}

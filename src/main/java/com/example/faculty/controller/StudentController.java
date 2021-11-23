package com.example.faculty.controller;

import com.example.faculty.database.entity.User;
import com.example.faculty.models.enums.CourseStatus;
import com.example.faculty.models.requests.UserUpdate;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.EnrollService;
import com.example.faculty.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class StudentController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    EnrollService enrollService;

    @GetMapping("/student")
    public String getStudent(Model model) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("student", userService.getUser(userId));
        return "/user/student";
    }

    @GetMapping("/student/update")
    public String showUpdateForm(Model model) {
        User student = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", student);
        return "/user/student/edit";
    }

    @PostMapping("/student/update")
    public String updateStudent(@Valid UserUpdate userUpdate, BindingResult result, Model model) {
        User student = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (result.hasErrors()) {
            return "/user/student/edit";
        }

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

        userService.updateUser(updatedUser);
        return "redirect:/student";
    }

    //<a th:href="@{/delete/{id}(id=${user.id})}">Delete</a>
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/index";
    }

    // TODO: 18.11.2021 check exception
    @GetMapping("/enroll/course/{courseId}")
    public String enroll(@PathVariable("courseId") Long courseId) throws Exception {
        Long studentId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(studentId );
        enrollService.enroll(studentId, courseId);
        return "redirect:/courses";
    }

}

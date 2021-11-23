package com.example.faculty.controller;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.dto.TopicDto;
import com.example.faculty.models.dto.UserCreateDto;
import com.example.faculty.models.dto.UserUpdate;
import com.example.faculty.services.implementation.EmailSenderService;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.TopicService;
import com.example.faculty.services.interfaces.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdministratorController {


    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/admin")
    public String getStudent(Model model) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        model.addAttribute("admin", userService.getUser(userId));
        return "/user/admin";
    }

    @GetMapping("/admin/update")
    public String showUpdateAdminForm(Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", admin);
        return "/user/admin/edit";
    }

    @PostMapping("/admin/update")
    public String updateAdmin(@Valid UserUpdate userUpdate, BindingResult result) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (result.hasErrors()) {
            return "/user/admin/edit";
        }

        User updatedUser = User.builder()
                .id(admin.getId())
                .firstName(userUpdate.getFirstName())
                .secondName(userUpdate.getSecondName())
                .lastName(userUpdate.getLastName())
                .email(userUpdate.getEmail())
                .password(admin.getPassword())
                .roles(admin.getRoles())
                .userRoleName(admin.getUserRoleName())
                .build();
        userService.updateUser(updatedUser);
        return "redirect:/admin";
    }

    @GetMapping("/topicsAll")
    public String showTopicsList(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        return "/topic/all_topics";
    }

    @GetMapping("/topic/add")
    public ModelAndView addTopic(ModelAndView modelAndView, TopicDto topic) {
        modelAndView.addObject("topic", topic);
        modelAndView.addObject("condition", "add");
        modelAndView.setViewName("/topic/topic");
        return modelAndView;
    }

    @PostMapping("/topic/add")
    public String addTopic(@Valid @ModelAttribute("topic") TopicDto topic, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("condition", "add");
            return "/topic/topic";
        }
        topicService.createTopic(topic.getName());
        return "redirect:/topics";
    }

    @GetMapping("/topic/update/{id}")
    public String showUpdateTopicForm(@PathVariable("id") long id, Model model) {
        Topic topic = topicService.findTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("condition", "edit");
        return "/topic/topic";
    }

    @PostMapping("/topic/update/{id}")
    public String updateTopic(@PathVariable("id") long id, @Valid TopicDto topicDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/topic/topic";
        }
        Topic topic = topicService.updateTopic(id, topicDto.getName());
        return "redirect:/topics";
    }

    @GetMapping("/topic/delete/{id}")
    public String deleteTopic(@PathVariable("id") long id) {
        topicService.deleteTopic(id);
        return "redirect:/topics";
    }

    // TODO: 16.11.2021  add param name
    @GetMapping("/topics")
    public String topicsGet(Model model,
                            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        model.addAttribute("topics", topicService.getTopicsPage(pageNumber, size));
        return "/topic/all_topics";
    }


    // TODO: 16.11.2021  add params
    @GetMapping("/students")
    public String studentsGet(Model model,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        model.addAttribute("students", userService.getStudentsPage(pageNumber, size));
        return "/user/student/all_students";
    }

    // TODO: 16.11.2021  add params
    // TODO: 16.11.2021 add list as drop down with teacher`s courses
    @GetMapping("/teachers")
    public String teachersGet(Model model,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "4") int size) {

        model.addAttribute("teachers", userService.getTeachersPage(pageNumber, size));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "/user/teacher/all_teachers";
    }

    @GetMapping("/teacher/create")
    public ModelAndView addTeacherForm(ModelAndView modelAndView, UserCreateDto userCreate) {
        modelAndView.addObject("teacher", userCreate);
        modelAndView.setViewName("/user/teacher/create");
        return modelAndView;
    }

    @PostMapping("/teacher/add")
    public String addTeacher(@Valid UserCreateDto userCreate, BindingResult result) {

        if (result.hasErrors()) {
            return "/user/teacher/create";
        }

        String password = RandomString.make(30);
        userService.createTeacher(userCreate, password);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userCreate.getEmail());
        mailMessage.setSubject("Register as a teacher");
        mailMessage.setFrom("test.email.mariia@gmail.com");

        mailMessage.setText("Hello, \n"
                + "You have been register as a teacher. \n"
                + "To sign in use this data:\n"
                + "email: " + userCreate.getEmail()
                + "\npassword: " + password
                + "\nChange your password you can in your profile page: \n"
                + "You can sign in via this link "
                + "http://localhost:8080/login"
                + "\n\nIgnore this email if you do remember your password, "
                + "or you have not made the request.</p>");

        emailSenderService.sendEmail(mailMessage);

        return "redirect:/teachers";
    }

    @GetMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/teachers";
    }

}

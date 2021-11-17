package com.example.faculty.controller;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.TopicDto;
import com.example.faculty.models.requests.UserCreateDto;
import com.example.faculty.models.requests.UserUpdate;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);
        return "/user/admin";
    }

    @GetMapping("/admin/update")
    public String showUpdateAdminForm(Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", admin);
        return "/user/admin/edit";
    }

    // TODO: 17.11.2021 not working updated teacher? but it store in db need to login one more time
    @PostMapping("/admin/update")
    public String updateAdmin(@Valid UserUpdate userUpdate, BindingResult result, Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

//        if (result.hasErrors()) {
//            return "redirect:/student/edit";
//        }
        userService.updateUser(updatedUser);
        model.addAttribute("user", updatedUser);
        return "redirect:/admin";
    }

    @GetMapping("/topicsAll")
    public String showTopicsList(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        return "/topic/all_topics";
    }

    @GetMapping("/topic/add")
    public ModelAndView addTopic(ModelAndView modelAndView, Model model, @Valid TopicDto topic) {
        modelAndView.addObject("topic", topic);
        modelAndView.addObject("condition", "add");
        modelAndView.setViewName("/topic/topic");
        return modelAndView;
    }

    @PostMapping("/topic/add")
    public String addTopic(@Valid TopicDto topic, BindingResult result, Model model) {
        topicService.createTopic(topic.getName());
        return "redirect:/topic/topics";
    }

    // TODO: 16.11.2021 not working 
    @GetMapping("/topic/edit/{id}")
    public ModelAndView showUpdateTopicForm(@PathVariable("id") long id, ModelAndView modelAndView, Model model) {
        Topic topic = topicService.findTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("condition", "edit");
        modelAndView.setViewName("/topic/topic");
        return modelAndView;
    }

    @PostMapping("/topic/update/{id}")
    public String updateTopic(@PathVariable("id") long id, @Valid TopicDto topicDto, Model model) {
        Topic topic = topicService.updateTopic(id, topicDto.getName());
        return "redirect:/topic/topics";
    }

    @GetMapping("/topic/delete/{id}")
    public String deleteTopic(@PathVariable("id") long id, Model model) {
        topicService.deleteTopic(id);
        return "redirect:/topic/topics";
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
    public ModelAndView addTeacherForm(ModelAndView modelAndView, Model model, UserCreateDto userCreate) {
        modelAndView.addObject("teacher", userCreate);
        modelAndView.setViewName("/user/teacher/create");
        return modelAndView;
    }

    @PostMapping("/teacher/add")
    public String addTeacher(@Valid UserCreateDto userCreate, BindingResult result, Model model) {

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

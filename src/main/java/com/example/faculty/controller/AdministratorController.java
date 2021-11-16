package com.example.faculty.controller;

import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.TopicDto;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.TopicService;
import com.example.faculty.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/admin")
    public String getStudent(Model model) {
        User admin = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", admin);
        return "/user/admin";
    }

    // TODO: 15.11.2021 test
    @GetMapping("/admin/edit")
    public String showUpdateAdminForm(Model model) {
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

    @GetMapping("/topicsAll")
    public String showTopicsList(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        return "/topic/all_topics";
    }

    @GetMapping("/topic/add")
    public ModelAndView addTopic(ModelAndView modelAndView, Model model, TopicDto topic) {
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
    public ModelAndView showUpdateTopicForm(@PathVariable("id") long id,ModelAndView modelAndView,  Model model) {
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

//    @GetMapping("/admin/students")
//    public String studentsGet(Model model,
//                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
//                              @RequestParam(value = "size", required = false, defaultValue = "2") int size,
//                              @RequestParam(value = "name", defaultValue = "") String name) {
//        model.addAttribute("name", name);
//        model.addAttribute("students", userService.getStudentsPage(name, pageNumber, size));
//        return "/users/admin/studentList";
//    }
}

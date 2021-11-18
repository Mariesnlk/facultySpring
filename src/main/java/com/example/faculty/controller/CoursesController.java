package com.example.faculty.controller;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.CourseDto;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.TopicService;
import com.example.faculty.services.interfaces.UserService;
import com.example.faculty.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CoursesController {

    @Autowired
    CourseService courseService;

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    // TODO: 17.11.2021 not working filters
    @GetMapping("/courses")
    public String coursesGet(Model model,
                             @RequestParam(value = "courseName", defaultValue = "") String courseName,
                             @RequestParam(value = "duration", defaultValue = "0") Integer duration,
                             @RequestParam(value = "studentsAmount", defaultValue = "0") Integer studentsAmount,
                             @RequestParam(value = "topic", defaultValue = "...") String topic,
                             @RequestParam(value = "teacher", defaultValue = "") String teacher,
                             @RequestParam(value = "sortType", defaultValue = "ASC") String sortType,
                             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "2") int size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("courseName", courseName);
        model.addAttribute("duration", duration);
        model.addAttribute("studentsAmount", studentsAmount);
        model.addAttribute("classes", setBtnClass(sortType));
        model.addAttribute("topicList", topicService.getAllTopics());
        model.addAttribute("teacher", teacher);
        model.addAttribute("sortType", sortType);
        model.addAttribute("courses", courseService.getCoursesPage(courseName, duration, studentsAmount, topic, teacher, pageNumber, size, sortType));
        model.addAttribute("user", user);
        return "/courses/all_Courses";
    }

    private List<String> setBtnClass(String sortType) {
        if (sortType.equals("ASC")) return List.of("btn btn-primary", "btn btn-outline-danger");
        return List.of("btn btn-outline-primary", "btn btn-danger");
    }

    @GetMapping("/courses/create")
    public String createCourseGet(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        model.addAttribute("teachers", userService.allTeachers());
        model.addAttribute("course", new Course());
        model.addAttribute("condition", "create");
        return "/courses/course";
    }

    @PostMapping("/courses/create")
    public String createCoursePost(Model model,
                                   @RequestParam("topicId") Long topicId,
                                   @RequestParam("teacherId") Long teacherId,
                                   @ModelAttribute("course") @Valid CourseDto courseDto, BindingResult bindingResult) {

//        System.out.println("bindingResult.hasErrors() :" + bindingResult.hasErrors());
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("topics", topicService.getAllTopics());
//            model.addAttribute("teachers", userService.allTeachers());
//            return "redirect:/courses/create";
//        }

        courseDto.setTopicId(topicService.findTopicById(topicId));
        courseDto.setTeacherId(userService.getUser(teacherId));
        courseService.createCourse(courseDto);
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String showUpdateTopicForm(@PathVariable("id") long id, ModelAndView modelAndView, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("topics", topicService.getAllTopics());
        model.addAttribute("teachers", userService.allTeachers());
        model.addAttribute("statuses", Utility.getAllCoursesStatus());
        model.addAttribute("condition", "edit");
        return "/courses/course";
    }

    @PostMapping("/courses/update/{id}")
    public String editCourse(@PathVariable("id") long id, @Valid CourseDto courseDto, Model model) {
        courseService.updateCourse(id, courseDto);
        return "redirect:/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") long id, Model model) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    // TODO: 17.11.2021 not working filters
    @GetMapping("/my_courses")
    public String coursesGet(Model model,
                             @RequestParam(value = "courseName", defaultValue = "") String courseName,
                             @RequestParam(value = "duration", defaultValue = "0") Integer duration,
                             @RequestParam(value = "studentsAmount", defaultValue = "0") Integer studentsAmount,
                             @RequestParam(value = "topic", defaultValue = "...") String topic,
                             @RequestParam(value = "teacher", defaultValue = "") String teacher,
                             @RequestParam(value = "sortType", defaultValue = "ASC") String sortType,
                             @RequestParam(value = "status", defaultValue = "...") String status,
                             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "2") int size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("courseName", courseName);
        model.addAttribute("duration", duration);
        model.addAttribute("studentsAmount", studentsAmount);
        model.addAttribute("classes", setBtnClass(sortType));
        model.addAttribute("topicList", topicService.getAllTopics());
        model.addAttribute("teacher", teacher);
        model.addAttribute("sortType", sortType);
        // TODO: 17.11.2021 fix dropdown in ui
        model.addAttribute("statusList", Utility.getAllCoursesStatus());
        model.addAttribute("courses", courseService.getCoursesPage(courseName, duration, studentsAmount,
                topic, teacher, status, pageNumber, size, sortType));
        model.addAttribute("user", user);
        return "/courses/all_Courses";
    }

}

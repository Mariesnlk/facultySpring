package com.example.faculty.controller;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.dto.CourseDto;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.GradeBookService;
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

    @Autowired
    GradeBookService gradeBookService;


    @GetMapping("/courses")
    public String coursesGet(Model model,
                             @RequestParam(value = "courseName", defaultValue = "") String courseName,
                             @RequestParam(value = "duration", defaultValue = "0") Integer duration,
                             @RequestParam(value = "studentsAmount", defaultValue = "0") Integer studentsAmount,
                             @RequestParam(value = "topic", defaultValue = "...") String topic,
                             @RequestParam(value = "teacher", defaultValue = "") String teacher,
                             @RequestParam(value = "status", defaultValue = "...") String status,
                             @RequestParam(value = "sortType", defaultValue = "ASC") String sortType,
                             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("courseName", courseName);
        model.addAttribute("duration", duration);
        model.addAttribute("studentsAmount", studentsAmount);
        model.addAttribute("classes", setBtnClass(sortType));
        model.addAttribute("topicList", topicService.getAllTopics());
        model.addAttribute("teacher", teacher);
        model.addAttribute("sortType", sortType);
        model.addAttribute("statusList", Utility.getAllCoursesStatus());
        model.addAttribute("courses", courseService.getCoursesPage(courseName, duration, studentsAmount,
                topic, teacher, status, pageNumber, size, sortType));
        model.addAttribute("user", user);
        return "/courses/all_Courses";
    }

    private List<String> setBtnClass(String sortType) {
        if (sortType.equals("ASC")) return List.of("btn btn-primary", "btn btn-outline-danger");
        return List.of("btn btn-outline-primary", "btn btn-danger");
    }

    @GetMapping("/courses/create")
    public ModelAndView createCourseGet(ModelAndView modelAndView, Model model, CourseDto courseDto) {
        model.addAttribute("topics", topicService.getAllTopics());
        model.addAttribute("teachers", userService.allTeachers());
        model.addAttribute("statusList", Utility.getAllCoursesStatus());
        model.addAttribute("course", courseDto);
        model.addAttribute("condition", "create");
        modelAndView.setViewName("/courses/course");
        return modelAndView;
    }

    @PostMapping("/courses/create")
    public String createCoursePost(@ModelAttribute("course") @Valid CourseDto courseDto,
                                   BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("condition", "create");
            model.addAttribute("topics", topicService.getAllTopics());
            model.addAttribute("teachers", userService.allTeachers());
            model.addAttribute("statusList", Utility.getAllCoursesStatus());
            return "/courses/course";
        }

        courseService.createCourse(courseDto);
        return "redirect:/courses";
    }

    @GetMapping("/courses/update/{id}")
    public String showUpdateTopicForm(@PathVariable("id") long id, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("topics", topicService.getAllTopics());
        model.addAttribute("teachers", userService.allTeachers());
        model.addAttribute("statusList", Utility.getAllCoursesStatus());
        model.addAttribute("condition", "edit");
        return "/courses/course";
    }

    @PostMapping("/courses/update/{id}")
    public String editCourse(@PathVariable("id") long id,
                             @ModelAttribute("course") @Valid CourseDto courseDto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            Course course = courseService.findCourseById(id);
            model.addAttribute("course", course);
            model.addAttribute("topics", topicService.getAllTopics());
            model.addAttribute("teachers", userService.allTeachers());
            model.addAttribute("statusList", Utility.getAllCoursesStatus());
            model.addAttribute("condition", "edit");
            return "/courses/course";
        }
        courseService.updateCourse(id, courseDto);
        return "redirect:/courses";
    }

    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/my_courses")
    public String myCoursesGet(Model model,
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
        model.addAttribute("statusList", Utility.getAllCoursesStatus());
        model.addAttribute("courses", courseService.getStudentCoursesPage(courseName, duration, studentsAmount,
                topic, teacher, status, user.getId(), pageNumber, size, sortType));
        model.addAttribute("user", user);
        return "/courses/my_courses";
    }

    @GetMapping("/teacher_courses")
    public String teacherCoursesGet(Model model,
                                    @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("courses", courseService.findAllCoursesByTeacher(user, pageNumber, size));
        model.addAttribute("user", user);
        return "/courses/teacher_courses";
    }


    @GetMapping("/courses/course/{id}")
    public String courseInfoGet(Model model,
                                @PathVariable("id") Long id,
                                @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        model.addAttribute("course", courseService.findCourseById(id));
        model.addAttribute("studentsList", userService.findAllStudentsByIdCourse(id, pageNumber, size));
        return "/courses/course_info";
    }

    @PostMapping(value = "/course/{courseId}/student/{studentId}")
    public String saveStudentMark(@PathVariable("courseId") Long courseId,
                                  @PathVariable("studentId") Long studentId,
                                  @RequestParam("mark") Integer mark) {
        gradeBookService.saveMark(studentId, courseId, mark);
        return "redirect:/courses/course/{courseId}";
    }

}

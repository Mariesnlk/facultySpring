package com.example.faculty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("/student")
    public String student() {
        return "user/student";
    }

    @GetMapping("/admin")
    public String admin() {
        return "user/admin";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "user/teacher";
    }
}
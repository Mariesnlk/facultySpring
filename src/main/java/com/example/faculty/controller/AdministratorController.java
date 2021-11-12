//package com.example.faculty.controller;
//
//import com.example.faculty.database.entity.User;
//import com.example.faculty.services.interfaces.UserService;
//import com.example.faculty.util.mvc_responses.model.MvcResponse;
//import com.example.faculty.util.mvc_responses.model.MvcResponseError;
//import com.example.faculty.util.mvc_responses.model.MvcResponseObject;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping(value = "/api")
//public class AdministratorController {
//
//    private final UserService userService;
//
//    private final List<String> admins = Arrays.asList("mariesnlk90@gmail.com", "mariia.synelnyk@ukma.edu.ua");
//
//
//    @RequestMapping(value = "/create_teacher", method = RequestMethod.POST, produces = "application/json")
//    public MvcResponse createTeacher(HttpServletRequest request, HttpServletResponse response,
//                                         @RequestParam(value = "page", defaultValue = "0", required = false) int page,
//                                         @RequestParam(value = "size", defaultValue = "10", required = false) int size,
//                                         @RequestParam(value = "showZeroBalance", defaultValue = "true", required = false) boolean isZeroBalance)
//    {
//        User user = userService.getUser(request);
//
//        if (!admins.contains(user.getEmail())) {
//            throw new UsernameNotFoundException("User with email: " + user.getEmail() + " is not admin");
//        }
//
//        try {
//            return new MvcResponseObject(200, userService.createTeacher());
//        } catch (Exception ex) {
//            return new MvcResponseError(400, "Error can`t get user statistics");
//        }
//    }
//
//}

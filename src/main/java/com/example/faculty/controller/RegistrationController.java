package com.example.faculty.controller;

import com.example.faculty.database.entity.ConfirmationToken;
import com.example.faculty.database.entity.User;
import com.example.faculty.database.repository.ConfirmationTokenRepository;
import com.example.faculty.models.requests.user.UserDto;
import com.example.faculty.services.implementation.EmailSenderService;
import com.example.faculty.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

//    @PostMapping
//    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
//                                      BindingResult result) {
//
//        User existing = userService.findByEmail(userDto.getEmail());
//        if (existing != null) {
//            result.rejectValue("email", null, "There is already an account registered with that email");
//        }
//
//        if (result.hasErrors()) {
//            return "registration";
//        }
//
//        userService.save(userDto);
//        return "redirect:/registration?success";
//    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserDto user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, UserDto userDto) {
        User existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            modelAndView.addObject("message", "This email already exists!");
            modelAndView.setViewName("error");
        } else {
            //userService.saveStudent(userDto);

            ConfirmationToken confirmationToken = new ConfirmationToken(userService.saveStudent(userDto));

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userDto.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("test.email.mariia@gmail.com");

            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationTokenName());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("email", userDto.getEmail());

            modelAndView.setViewName("successfulRegisteration");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationTokenName(confirmationToken);

        if (token != null) {
            User user = userService.findByEmail(token.getUser().getEmail());
            user.setRegistered(true);
            userService.updateUser(user);
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }

}
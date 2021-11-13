package com.example.faculty.services.interfaces;

import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User saveStudent(UserDto registration);

    User saveStudent(User user);

    User updateUser(User user);
}
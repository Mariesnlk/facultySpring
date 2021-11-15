package com.example.faculty.services.interfaces;

import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.UserDto;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User saveStudent(UserDto registration);

    User saveStudent(User user);

    User updateUser(User user);

    User getUser(Long userId);

    void updateResetPasswordToken(String token, String email) throws NotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    void deleteUser(Long userId);

}
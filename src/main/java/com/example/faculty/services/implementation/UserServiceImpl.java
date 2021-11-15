package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Role;
import com.example.faculty.database.entity.User;
import com.example.faculty.database.repository.UserRepository;
import com.example.faculty.models.enums.Roles;
import com.example.faculty.models.requests.UserDto;
import com.example.faculty.services.interfaces.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User findByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User saveStudent(UserDto registration) {
        return userRepository.save(User.builder()
                .firstName(registration.getFirstName())
                .secondName(registration.getSecondName())
                .lastName(registration.getLastName())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .roles(setStudentRole())
                .build());
    }

    @Override
    public User saveStudent(User user) {

        return userRepository.save(User.builder()
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(setStudentRole())
                .build());
    }

    private Set<Role> setStudentRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, Roles.STUDENT.name()));
        return roles;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.getUserByEmail(username);

        /*User user = userRepository.getUserByEmail(username);
        return (UserDetails) (user.isRegistered() ? user : null);*/
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new NotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findUserByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        userRepository.delete(user);
    }

}

package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Role;
import com.example.faculty.database.entity.User;
import com.example.faculty.database.repository.UserRepository;
import com.example.faculty.models.enums.Roles;
import com.example.faculty.models.requests.StudentMarkDto;
import com.example.faculty.models.requests.UserCreateDto;
import com.example.faculty.models.requests.UserDto;
import com.example.faculty.services.interfaces.UserService;
import com.example.faculty.util.paging.Paged;
import com.example.faculty.util.paging.Paging;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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
                .userRoleName(Roles.STUDENT.name())
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
                .userRoleName(Roles.STUDENT.name())
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
        return userRepository.getUserByEmail(username);

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

    @Override
    public Paged getStudentsPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<User> postPage = setStudents(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    // TODO: 16.11.2021 can be also found by params
    private Page<User> setStudents(Pageable pageable) {
//        if (name.isEmpty())
//            return findAllTopics(pageable);
//        return findStudentsByPIB(name, pageable);
        return userRepository.getAllByUserRoleNameOrderByCreatedDate(Roles.STUDENT.name(), pageable);
    }

    @Override
    public Paged getTeachersPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<User> postPage = setTeachers(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    // TODO: 16.11.2021 can be also found by params
    private Page<User> setTeachers(Pageable pageable) {
//        if (name.isEmpty())
//            return findAllTopics(pageable);
//        return findStudentsByPIB(name, pageable);
        return userRepository.getAllByUserRoleNameOrderByCreatedDate(Roles.TEACHER.name(), pageable);
    }

    @Override
    public List<User> allTeachers() {
        return userRepository.findAllByUserRoleNameOrderByCreatedDate(Roles.TEACHER.name());
    }

    @Override
    public User createTeacher(UserCreateDto userCreateDto, String password) {
        return userRepository.save(User.builder()
                .firstName(userCreateDto.getFirstName())
                .secondName(userCreateDto.getSecondName())
                .lastName(userCreateDto.getLastName())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(password))
                .roles(setTeacherRole())
                .userRoleName(Roles.TEACHER.name())
                .build());
    }

    @Override
    public Paged findAllStudentsByIdCourse(Long courseId, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<StudentMarkDto> postPage = userRepository.findAllStudentsByIdCourse(courseId, request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    private Set<Role> setTeacherRole() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2, Roles.TEACHER.name()));
        return roles;
    }

}

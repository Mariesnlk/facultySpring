package com.example.faculty.services.interfaces;


import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.CourseDto;
import com.example.faculty.util.paging.Paged;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    Course createCourse(CourseDto courseDto);

    Course updateCourse(Long courseId, CourseDto courseDto);

    Course updateCourse(Course course);

    Course findCourseById(Long courseId);

    void deleteCourse(Long courseId);

    Paged getCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic,
                         String teacher, String status, int pageNumber, int size, String sortType);

    Paged getStudentCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic,
                         String teacher, String courseStatus, Long studentId, int pageNumber, int size, String sortType);

    Page<Course> findAllCourses(Pageable pageable);

    Page<Course> findAllStudentCourses(Long studentId, Pageable pageable);

    List<String> findAllCourseNames();

    List<Integer> findAllDurations();

    List<Integer> findAllStudentsAmount();

    List<String> findCourseNameByName(String name);

    List<Topic> findAllTopics();

    List<User> findAllTeacherNames();

    List<User> findTeacherIdByName(String name);

    Paged findAllCoursesByTeacher(User teacherId, int pageNumber, int size);

}

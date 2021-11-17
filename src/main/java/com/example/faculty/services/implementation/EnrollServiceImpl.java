package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.Enroll;
import com.example.faculty.database.repository.EnrollRepository;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollServiceImpl implements EnrollService {

    @Autowired
    EnrollRepository enrollRepository;

    @Autowired
    CourseService courseService;

    @Override
    public void enroll(Long studentId, Long courseId) throws Exception {
        Course course = courseService.findCourseById(courseId);

        if (enrollRepository.existsEnrollByIdCourseAndIdUser(courseId, studentId)) {
            enrollRepository.deleteByIdCourseAndIdUser(courseId, studentId);
            course.setEnrollStudents(course.getEnrollStudents() - 1);
        } else {
            if (course.getEnrollStudents() >= course.getStudentsAmount())
                throw new Exception("You can not enroll to course");
            enrollRepository.save(Enroll.builder().idCourse(courseId).idUser(studentId).build());
            course.setEnrollStudents(course.getEnrollStudents() + 1);
        }

        courseService.updateCourse(course);

    }
}

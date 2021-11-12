package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.repository.CourseRepository;
import com.example.faculty.exception.BadRequestException;
import com.example.faculty.models.enums.CourseStatus;
import com.example.faculty.models.requests.CourseDto;
import com.example.faculty.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    //only administrator can create, update, delete course

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course createCourse(CourseDto courseDto) {

        String courseName = courseDto.getCourseName();

        if (courseRepository.existsCourseByName(courseName))
            throw new BadRequestException("This course " + courseName + " is already exists");

        return courseRepository.save(Course.builder()
                .name(courseName)
                .duration(courseDto.getDuration())
                .studentsAmount(courseDto.getAmountOfStudent())
                .idTopic(courseDto.getTopicId())
                .idTeacher(courseDto.getTeacherId())
                .status(CourseStatus.NOT_STARTED)
                .build());
    }

    @Override
    public Course updateCourse(Long courseId, CourseDto courseDto) {
        Course course = findCourseById(courseId);
        course.setName(course.getName());
        course.setDuration(courseDto.getDuration());
        course.setStudentsAmount(courseDto.getAmountOfStudent());
        course.setStatus(courseDto.getCourseStatus());
        course.setIdTopic(courseDto.getTopicId());
        course.setIdTeacher(courseDto.getTeacherId());
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(Long courseId) {
        return courseRepository.findCourseById(courseId).orElseThrow(
                () -> new BadRequestException("Course with id " + courseId + " does not exist!"));
    }

    @Override
    public void changeCourseStatus(Long courseId, CourseStatus status) {
        Course course = findCourseById(courseId);
        course.setStatus(status);
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.delete(findCourseById(courseId));
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}

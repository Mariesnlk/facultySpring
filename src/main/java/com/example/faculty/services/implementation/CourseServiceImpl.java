package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.repository.CoursePagingRepository;
import com.example.faculty.database.repository.CourseRepository;
import com.example.faculty.exception.BadRequestException;
import com.example.faculty.models.enums.CourseStatus;
import com.example.faculty.models.requests.CourseDto;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.util.paging.Paged;
import com.example.faculty.util.paging.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CoursePagingRepository coursePagingRepository;

    @Override
    public Course createCourse(CourseDto courseDto) {

        String courseName = courseDto.getName();

        if (courseRepository.existsCourseByName(courseName))
            throw new BadRequestException("This course " + courseName + " is already exists");

        return courseRepository.save(Course.builder()
                .name(courseName)
                .duration(courseDto.getDuration())
                .studentsAmount(courseDto.getStudentsAmount())
                .topic(courseDto.getTopicId())
                .teacherId(courseDto.getTeacherId())
                .status(CourseStatus.NOT_STARTED)
                .build());
    }

    @Override
    public Course updateCourse(Long courseId, CourseDto courseDto) {
        Course course = findCourseById(courseId);
        course.setName(course.getName());
        course.setDuration(courseDto.getDuration());
        course.setStudentsAmount(courseDto.getStudentsAmount());
        course.setStatus(courseDto.getCourseStatus());
        course.setTopic(courseDto.getTopicId());
        course.setTeacherId(courseDto.getTeacherId());
        course.setStatus(courseDto.getCourseStatus());
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
    public Paged getCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic, String teacher, int pageNumber, int size, String sortType) {
        Pageable request = PageRequest.of(pageNumber - 1, size, setSort(sortType));
        Page<Course> postPage = setCourses(courseName, duration, studentsAmount, topic, teacher, request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    private Sort setSort(String sortType) {
        return sortType.equals("ASC")
                ? Sort.by("name").ascending()
                : Sort.by("name").descending();
    }

    private Page<Course> setCourses(String courseName, Integer duration, Integer studentsAmount, String topic, String teacher, Pageable pageable) {

        if (courseName.isEmpty() && duration == 0 && studentsAmount == 0
                && topic.equals("...") && teacher.isEmpty()) {
            return findAllCourses(pageable);
        }
        return findAllCoursesByParams(setCourseNameParam(courseName), setDurationParam(duration), setCapacityParam(studentsAmount),
                setTopicsParam(topic), setTeacherNameParam(teacher), pageable);
    }

    public Page<Course> findAllCoursesByParams(List<String> courseName, List<Integer> duration,
                                               List<Integer> capacity, List<String> topic,
                                               List<Integer> teacherId, Pageable pageable) {
        return coursePagingRepository.findAllByParams(courseName, duration, capacity, topic, teacherId, pageable);
    }

    private List<String> setCourseNameParam(String courseName) {
        return courseName.isEmpty() ? findAllCourseNames() : findCourseNameByName(courseName);
    }

    private List<Integer> setDurationParam(Integer duration) {
        return duration == 0 ? findAllDurations() : List.of(duration);
    }

    private List<Integer> setCapacityParam(Integer capacity) {
        return capacity == 0 ? findAllStudentsAmount() : List.of(capacity);
    }

    private List<String> setTopicsParam(String topics) {
        return topics.equals("...") ? findAllTopics() : List.of(topics);
    }

    private List<Integer> setTeacherNameParam(String teacherName) {
        return teacherName.isEmpty()
                ? findAllTeacherNames()
                : findTeacherIdByName(teacherName);
    }

    @Override
    public Page<Course> findAllCourses(Pageable pageable) {
        return courseRepository.findAllBy(pageable);
    }

    @Override
    public List<String> findAllCourseNames() {
        return coursePagingRepository.findAllCourseNames();
    }

    @Override
    public List<Integer> findAllDurations() {
        return coursePagingRepository.findAllDurations();
    }

    @Override
    public List<Integer> findAllStudentsAmount() {
        return coursePagingRepository.findAllStudentsAmount();
    }

    @Override
    public List<String> findCourseNameByName(String name) {
        return coursePagingRepository.findCourseNameByName(name);
    }

    @Override
    public List<String> findAllTopics() {
        return coursePagingRepository.findAllTopics();
    }

    @Override
    public List<Integer> findAllTeacherNames() {
        return coursePagingRepository.findAllTeacherNames();
    }

    @Override
    public List<Integer> findTeacherIdByName(String name) {
        return coursePagingRepository.findTeacherIdByName(name);
    }
}

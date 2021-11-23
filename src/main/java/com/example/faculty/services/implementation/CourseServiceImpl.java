package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.database.repository.CoursePagingRepository;
import com.example.faculty.database.repository.CourseRepository;
import com.example.faculty.database.repository.GradeBookRepository;
import com.example.faculty.exception.BadRequestException;
import com.example.faculty.models.enums.CourseStatus;
import com.example.faculty.models.requests.CourseDto;
import com.example.faculty.services.interfaces.CourseService;
import com.example.faculty.services.interfaces.TopicService;
import com.example.faculty.services.interfaces.UserService;
import com.example.faculty.util.Utility;
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

    @Autowired
    GradeBookRepository gradeBookRepository;

    @Autowired
    TopicService topicService;

    @Autowired
    UserService userService;

    @Override
    public Course createCourse(CourseDto courseDto) {

        String courseName = courseDto.getName();

        if (courseRepository.existsCourseByName(courseName))
            throw new BadRequestException("This course " + courseName + " is already exists");


        return courseRepository.save(Course.builder()
                .name(courseName)
                .duration(courseDto.getDuration())
                .studentsAmount(courseDto.getStudentsAmount())
                .topic(topicService.findTopicById(courseDto.getTopic()))
                .teacherId(userService.getUser(courseDto.getTeacherId()))
                .status(CourseStatus.NOT_STARTED.name())
                .enrollStudents(0)
                .build());
    }

    @Override
    public Course updateCourse(Long courseId, CourseDto courseDto) {
        Course course = findCourseById(courseId);
        course.setName(course.getName());
        course.setDuration(courseDto.getDuration());
        course.setStudentsAmount(courseDto.getStudentsAmount());
        course.setStatus(courseDto.getStatus());
        course.setTopic(topicService.findTopicById(courseDto.getTopic()));
        course.setTeacherId(userService.getUser(courseDto.getTeacherId()));
        course.setStatus(courseDto.getStatus());
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(Long courseId) {
        return courseRepository.findCourseById(courseId).orElseThrow(
                () -> new BadRequestException("Course with id " + courseId + " does not exist!"));
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.delete(findCourseById(courseId));
    }

    @Override
    public Paged getCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic, String teacher,
                                String status, int pageNumber, int size, String sortType) {
        Pageable request = PageRequest.of(pageNumber - 1, size, setSort(sortType));
        Page<Course> postPage = setCourses(courseName, duration, studentsAmount, topic, teacher, status, request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    @Override
    public Paged getStudentCoursesPage(String courseName, Integer duration, Integer studentsAmount, String topic, String teacher,
                                       String courseStatus, Long studentId, int pageNumber, int size, String sortType) {
        Pageable request = PageRequest.of(pageNumber - 1, size, setSort(sortType));
        Page<Course> postPage = setStudentCourses(courseName, duration, studentsAmount, topic, teacher, studentId, courseStatus, request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    private Sort setSort(String sortType) {
        return sortType.equals("ASC")
                ? Sort.by("name").ascending()
                : Sort.by("name").descending();
    }

    private Page<Course> setCourses(String courseName, Integer duration, Integer studentsAmount, String topic,
                                    String teacher, String status, Pageable pageable) {
        if (courseName.isEmpty() && duration == 0 && studentsAmount == 0
                && topic.equals("...") && status.equals("...") && teacher.isEmpty()) {
            return findAllCourses(pageable);
        }
        return findAllCoursesByParams(setCourseNameParam(courseName), setDurationParam(duration), setCapacityParam(studentsAmount),
                setTopicsParam(topic), setCourseStatusParam(status), setTeacherNameParam(teacher), pageable);
    }

    private Page<Course> setStudentCourses(String courseName, Integer duration, Integer studentsAmount, String topic,
                                           String teacher, Long studentId, String status, Pageable pageable) {

        if (courseName.isEmpty() && duration == 0 && studentsAmount == 0
                && topic.equals("...") && status.equals("...") && teacher.isEmpty()) {
            return findAllStudentCourses(studentId, pageable);
        }
        return findAllCoursesByParamsAndStudentId(setCourseNameParam(courseName), setDurationParam(duration), setCapacityParam(studentsAmount),
                setTopicsParam(topic), setTeacherNameParam(teacher), setCourseStatusParam(status), studentId, pageable);
    }

    public Page<Course> findAllCoursesByParams(List<String> courseName, List<Integer> duration,
                                               List<Integer> studentsAmount, List<Topic> topic,
                                               List<String> status, List<User> teacherId,
                                               Pageable pageable) {
        return coursePagingRepository.findByNameInAndDurationInAndStudentsAmountInAndTopicInAndStatusInAndTeacherIdIn(courseName, duration, studentsAmount, topic, status, teacherId, pageable);
    }

    public Page<Course> findAllCoursesByParamsAndStudentId(List<String> courseName, List<Integer> duration,
                                                           List<Integer> studentsAmount, List<Topic> topic,
                                                           List<User> teacherId, List<String> statuses,
                                                           Long studentId, Pageable pageable) {
        return coursePagingRepository.findAllCoursesByNewParamsAndStudent(courseName, duration, studentsAmount, topic, teacherId,
                statuses, studentId, pageable);
    }

    private List<String> setCourseNameParam(String courseName) {
        return courseName.isEmpty() ? findAllCourseNames() : findCourseNameByName(courseName);
    }

    private List<Integer> setDurationParam(Integer duration) {
        return duration == 0 ? findAllDurations() : List.of(duration);
    }

    private List<Integer> setCapacityParam(Integer studentsAmount) {
        return studentsAmount == 0 ? findAllStudentsAmount() : List.of(studentsAmount);
    }

    private List<String> setCourseStatusParam(String courseStatus) {
        return courseStatus.equals("...") ? Utility.getAllCoursesStatus() : List.of(courseStatus);
    }

    private List<Topic> setTopicsParam(String topics) {
        return topics.equals("...") ? findAllTopics() : List.of(topicService.findTopicById(Long.parseLong(topics)));
    }

    private List<User> setTeacherNameParam(String teacherName) {
        return teacherName.isEmpty()
                ? findAllTeacherNames()
                : findTeacherIdByName(teacherName);
    }

    @Override
    public Page<Course> findAllCourses(Pageable pageable) {
        return courseRepository.findAllBy(pageable);
    }

    @Override
    public Page<Course> findAllStudentCourses(Long studentId, Pageable pageable) {
        return coursePagingRepository.findAllCoursesByStudent(studentId, pageable);
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
    public List<Topic> findAllTopics() {
        return coursePagingRepository.findAllTopics();
    }

    @Override
    public List<User> findAllTeacherNames() {
        return coursePagingRepository.findAllTeacherNames();
    }

    @Override
    public List<User> findTeacherIdByName(String name) {
        return coursePagingRepository.findTeacherIdByName(name);
    }

    @Override
    public Paged findAllCoursesByTeacher(User teacher, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Course> postPage = setCoursesByTeacher(teacher, request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    private Page<Course> setCoursesByTeacher(User teacher, Pageable pageable) {
        return courseRepository.findCoursesByTeacherId(teacher, pageable);
    }
}

package com.example.faculty.database.repository;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.Topic;
import com.example.faculty.database.entity.User;
import com.example.faculty.models.dto.CoursesWithMyMarkDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePagingRepository extends PagingAndSortingRepository<Course, Long> {

    /*@Query("select c from Course c where " +
            "c.name in (:courseName) and " +
            "c.duration in (:duration) and " +
            "c.studentsAmount in (:studentsAmount) and " +
            "c.topic.name in (:topic) and " +
            "c.teacherId.id in (:teacherId) and " +
            "c.status in (:status) ")
    Page<Course> findAllByParams(@Param("courseName") List<String> courseName, @Param("duration") List<Integer> duration,
                                 @Param("studentsAmount") List<Integer> studentsAmount, @Param("topic") List<String> topic,
                                 @Param("status") List<String> status, @Param("teacherId") List<Integer> teacherId,
                                 Pageable pageable);*/


    Page<Course> findByNameInAndDurationInAndStudentsAmountInAndTopicInAndStatusInAndTeacherIdIn(List<String> courseName, List<Integer> duration,
                                                                                                 List<Integer> studentsAmount, List<Topic> topic,
                                                                                                 List<String> status, List<User> teacherId,
                                                                                                 Pageable pageable);

    @Query("select new com.example.faculty.models.dto.CoursesWithMyMarkDto(c.name, c.duration, c.studentsAmount, c.topic, c.teacherId, c.status, g.mark)  " +
            "from Course c " +
            "inner join Enroll e " +
            "on c.id = e.idCourse " +
            "left join GradeBook g " +
            "on g.idCourse = c.id " +
            "where e.idUser=:userId and " +
            "c.name in (:courseName) and " +
            "c.duration in (:duration) and " +
            "c.studentsAmount in (:studentsAmount) and " +
            "c.topic in (:topic) and " +
            "c.teacherId in (:teacherId) and " +
            "c.status in (:status) ")
    Page<CoursesWithMyMarkDto> findAllCoursesByNewParamsAndStudent(@Param("courseName") List<String> courseName, @Param("duration") List<Integer> duration,
                                                     @Param("studentsAmount") List<Integer> studentsAmount, @Param("topic") List<Topic> topic,
                                                     @Param("teacherId") List<User> teacherId, @Param("status") List<String> status,
                                                     @Param("userId") Long userId, Pageable pageable);


    @Query("select new com.example.faculty.models.dto.CoursesWithMyMarkDto(c.name, c.duration, c.studentsAmount, c.topic, c.teacherId, c.status, g.mark) " +
            "from Course c " +
            "inner join Enroll e " +
            "on c.id = e.idCourse " +
            "left join GradeBook g " +
            "on g.idCourse = c.id " +
            "where e.idUser=:userId")
    Page<CoursesWithMyMarkDto> findAllCoursesByStudent(@Param("userId") Long userId, Pageable pageable);

    @Query("select c from Course c where c.teacherId.id is null")
    List<Course> findCoursesWithoutTeacher();

    @Query("select distinct(c.name) from Course c")
    List<String> findAllCourseNames();

    @Query("select distinct(c.duration) from Course c")
    List<Integer> findAllDurations();

    @Query("select distinct(c.studentsAmount) from Course c")
    List<Integer> findAllStudentsAmount();

    @Query("select t from Topic t")
    List<Topic> findAllTopics();

    @Query("select t from User t")
    List<User> findAllTeacherNames();

    @Query("select c.name from Course c where lower(c.name) like lower(concat('%',:name,'%') )")
    List<String> findCourseNameByName(@Param("name") String name);

    @Query("select t from User t where lower(concat(t.lastName,' ',t.firstName,' ',t.secondName)) like lower(concat('%',:name,'%') )")
    List<User> findTeacherIdByName(@Param("name") String name);
}

package com.example.faculty.database.repository;

import com.example.faculty.database.entity.Course;
import com.example.faculty.models.enums.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePagingRepository extends PagingAndSortingRepository<Course, Long> {

    @Query("select c from Course c where " +
            "c.name in (:courseName) and " +
            "c.duration in (:duration) and " +
            "c.studentsAmount in (:studentsAmount) and " +
            "c.topic.name in (:topic) and " +
            "c.teacherId.id in (:teacherId)")
    Page<Course> findAllByParams(@Param("courseName") List<String> courseName, @Param("duration") List<Integer> duration,
                                 @Param("studentsAmount") List<Integer> studentsAmount, @Param("topic") List<String> topic,
                                 @Param("teacherId") List<Integer> teacherId, Pageable pageable);


    @Query("select c from Course c where " +
            "c.name in (:courseName) and " +
            "c.duration in (:duration) and " +
            "c.studentsAmount in (:studentsAmount) and " +
            "c.topic.name in (:topic) and " +
            "c.teacherId.id in (:teacherId) and " +
            "c.status in (:status)")
    Page<Course> findAllByNewParams(@Param("courseName") List<String> courseName, @Param("duration") List<Integer> duration,
                                    @Param("studentsAmount") List<Integer> studentsAmount, @Param("topic") List<String> topic,
                                    @Param("teacherId") List<Integer> teacherId, @Param("status") List<String> status,
                                    Pageable pageable);


    @Query("select c from Course c where c.teacherId.id is null")
    List<Course> findCoursesWithoutTeacher();

    @Query("select distinct(c.name) from Course c")
    List<String> findAllCourseNames();

    @Query("select distinct(c.duration) from Course c")
    List<Integer> findAllDurations();

    @Query("select distinct(c.studentsAmount) from Course c")
    List<Integer> findAllStudentsAmount();

    @Query("select t.name from Topic t")
    List<String> findAllTopics();

    @Query("select t.id from User t")
    List<Integer> findAllTeacherNames();

    @Query("select c.name from Course c where lower(c.name) like lower(concat('%',:name,'%') )")
    List<String> findCourseNameByName(@Param("name") String name);

    @Query("select t.id from User t where lower(concat(t.lastName,' ',t.firstName,' ',t.secondName)) like lower(concat('%',:name,'%') )")
    List<Integer> findTeacherIdByName(@Param("name") String name);
}

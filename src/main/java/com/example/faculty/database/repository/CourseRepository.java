package com.example.faculty.database.repository;

import com.example.faculty.database.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Optional<Course> findCourseById(Long courseId);

    Boolean existsCourseByName(String name);

    Page<Course> findAll(Pageable pageable);

    List<Course> findCoursesByIdTopic(Long topicId);

    //List<Course> findCoursesByNameOrderByCreated(String courseName);

    Page<Course> findCoursesByIdTopic(Long topicId, Pageable pageable);

    //Page<Course> findCoursesByNameOrderByCreated(String courseName, Pageable pageable);

//    @Query(value = "select c from Course c where " +
//            "c.name in (:name) and " +
//            "c.duration in (:duration) and " +
//            "c.studentsAmount in (:studentsAmount) and " +
//            "c.idTeacher in (:teacherId)", nativeQuery = true)
//    List<Course> findAllByParams(@Param("name") List<String> name, @Param("duration") List<Integer> duration,
//                                 @Param("studentsAmount") List<Integer> studentsAmount,
//                                 @Param("idTeacher") List<Integer> teacherId, Pageable pageable);

}

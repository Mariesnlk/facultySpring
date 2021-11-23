package com.example.faculty.database.repository;

import com.example.faculty.database.entity.Course;
import com.example.faculty.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Optional<Course> findCourseById(Long courseId);

    Boolean existsCourseByName(String name);

    Page<Course> findAllBy(Pageable pageable);

    Page<Course> findCoursesByTeacherId(User teacherId, Pageable pageable);
}

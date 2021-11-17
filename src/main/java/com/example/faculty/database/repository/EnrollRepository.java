package com.example.faculty.database.repository;

import com.example.faculty.database.entity.Enroll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollRepository extends CrudRepository<Enroll, Long> {

    Boolean existsEnrollByIdCourseAndIdUser(Long courseId, Long studentId);

    void deleteByIdCourseAndIdUser(Long courseId, Long studentId);

}

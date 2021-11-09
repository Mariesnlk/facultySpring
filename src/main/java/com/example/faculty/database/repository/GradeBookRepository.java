package com.example.faculty.database.repository;

import com.example.faculty.database.entity.GradeBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeBookRepository extends CrudRepository<GradeBook, Long> {
}

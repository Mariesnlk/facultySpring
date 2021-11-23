package com.example.faculty.services.implementation;

import com.example.faculty.database.entity.GradeBook;
import com.example.faculty.database.repository.GradeBookRepository;
import com.example.faculty.services.interfaces.GradeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeBookServiceImpl implements GradeBookService {

    @Autowired
    GradeBookRepository gradeBookRepository;


    @Override
    public void saveMark(Long studentId, Long courseId, Integer mark) {
        if (!gradeBookRepository.existsGradeBookByIdCourseAndIdStudent(courseId, studentId))
            gradeBookRepository.save(GradeBook.builder().idStudent(studentId).idCourse(courseId).mark(mark).build());
        else {
            GradeBook gradeBook = gradeBookRepository.findGradeBookByIdCourseAndIdStudent(courseId, studentId);
            gradeBook.setMark(mark);
            gradeBookRepository.save(gradeBook);
        }
    }

}

package com.example.faculty.database.repository;

import com.example.faculty.database.entity.User;
import com.example.faculty.models.requests.StudentMarkDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByEmail(String email);

    User findUserByResetPasswordToken(String token);

    List<User> findAllByUserRoleNameOrderByCreatedDate(String userRoleName);

    Page<User> getAllByUserRoleNameOrderByCreatedDate(String userRoleName, Pageable pageable);

    @Query("select new com.example.faculty.models.requests.StudentMarkDto(u.firstName, u.secondName, u.lastName, g.mark) " +
            "from User u " +
            "left join Enroll e " +
            "on u.id = e.idUser " +
            "left join GradeBook g " +
            "on g.idStudent = e.idUser " +
            "where e.idCourse=:courseId")
    Page<StudentMarkDto> findAllStudentsByIdCourse(@Param("courseId") Long courseId, Pageable pageable);

}

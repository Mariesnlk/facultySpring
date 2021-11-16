package com.example.faculty.database.repository;

import com.example.faculty.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByEmail(String email);

    User findUserByResetPasswordToken(String token);

    List<User> findAllByUserRoleNameOrderByCreatedDate(String userRoleName);

    Page<User> getAllByUserRoleNameOrderByCreatedDate(String userRoleName, Pageable pageable);

}

package com.example.faculty.database.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

//@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
//@Data
//@Entity
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();//.getTime();
}
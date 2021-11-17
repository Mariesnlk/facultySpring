package com.example.faculty.database.entity;

import com.example.faculty.models.enums.CourseStatus;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "course")
public class Course  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();//.getTime();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private User teacherId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private Integer duration;

    @Column(name = "students_amount")
    private Integer studentsAmount;

    @Column(name = "enroll_students")
    private Integer enrollStudents;

    @Column(name = "status")
    private String status;
}

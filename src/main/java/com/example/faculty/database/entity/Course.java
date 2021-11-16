package com.example.faculty.database.entity;

import com.example.faculty.models.enums.CourseStatus;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();//.getTime();

    @Column(name = "id_topic")
    private Long idTopic;

    @Column(name = "id_teacher")
    private Long idTeacher;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private int duration;

    @Column(name = "students_amount")
    private int studentsAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
}

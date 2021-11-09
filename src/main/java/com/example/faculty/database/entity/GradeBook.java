package com.example.faculty.database.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gradebook")
public class GradeBook extends BaseEntity {

    @Column(name = "id_course")
    private Long idCourse;

    @Column(name = "id_student")
    private Long idStudent;

    @Column(name = "mark")
    private int mark;
}

package com.example.faculty.database.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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

    @Min(1)
    @Max(100)
    @Column(name = "mark")
    private int mark;
}

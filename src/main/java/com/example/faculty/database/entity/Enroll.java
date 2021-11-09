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
@Table(name = "enroll")
public class Enroll extends BaseEntity {

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_course")
    private Long idCourse;
}

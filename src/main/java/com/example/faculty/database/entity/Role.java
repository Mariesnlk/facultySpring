package com.example.faculty.database.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
//@Builder
public class Role implements GrantedAuthority {

    @Id
    @Column
    private Integer id;

    @Column
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    @Column
    private Set<User> users;

    public Role() {

    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
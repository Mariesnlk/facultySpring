package com.example.faculty.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT, TEACHER, ADMINISTRATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}

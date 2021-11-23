package com.example.faculty.models.dto;

import com.example.faculty.util.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserCreateDto {
    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Z][a-z][А-Я][а-яєі]*", message = "Not valid first name")
    private String firstName;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Z][a-z][А-Я][а-яєі]*", message = "Not valid second name")
    private String secondName;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Z][a-z][А-Я][а-яєі]*", message = "Not valid last name")
    private String lastName;

    @ValidEmail
    @NotNull
    @NotEmpty
    @Email
    private String email;


}

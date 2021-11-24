package com.example.faculty.models.dto;

import com.example.faculty.util.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDto {
    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Za-zА-Яа-яєі]*", message = "Not valid first name")
    private String firstName;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Za-zА-Яа-яєі]*", message = "Not valid second name")
    private String secondName;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Za-zА-Яа-яєі]*", message = "Not valid last name")
    private String lastName;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Not valid user password")
    private String password;

    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Not valid user password")
    private String confirmPassword;

    @ValidEmail
    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Email(message = "Not valid email")
    private String email;

    @AssertTrue
    private Boolean terms;
}

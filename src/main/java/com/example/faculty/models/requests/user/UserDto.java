package com.example.faculty.models.requests.user;

import com.example.faculty.util.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDto {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Z][a-z]*", message = "Not valid first name")
    private String firstName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Z][a-z]*", message = "Not valid second name")
    private String secondName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-Z][a-z]*", message = "Not valid last name")
    private String lastName;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "Not valid user password")
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @AssertTrue
    private Boolean terms;
}

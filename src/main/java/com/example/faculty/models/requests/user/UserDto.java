package com.example.faculty.models.requests.user;

import com.example.faculty.util.validator.PasswordMatches;
import com.example.faculty.util.validator.ValidEmail;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
@Data
public class UserDto {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String secondName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String confirmPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @AssertTrue
    private Boolean terms;
}

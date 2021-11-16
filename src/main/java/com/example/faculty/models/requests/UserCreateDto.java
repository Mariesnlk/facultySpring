package com.example.faculty.models.requests;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserCreateDto {
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
    @Email
    private String email;


}

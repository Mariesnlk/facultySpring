package com.example.faculty.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TopicDto {
    @NotNull(message = "Field can't be null!")
    @NotEmpty(message = "Field can't be empty!")
    @Pattern(regexp = "[A-Z][a-z][А-Я][а-яєі]*", message = "Not valid name")
    private String name;
}

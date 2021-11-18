package com.example.faculty.models.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TopicDto {
    @NotNull
    @NotEmpty
    private String name;
}

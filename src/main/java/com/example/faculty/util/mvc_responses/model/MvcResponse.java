package com.example.faculty.util.mvc_responses.model;

import com.example.faculty.util.mvc_responses.resources.Statuses;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class MvcResponse {
    @Getter
    @Setter
    @JsonProperty("status")
    protected int status;

    public MvcResponse(int status) {
        this.status = status;
    }

    public MvcResponse(Statuses status) {
        this.status = status.getStatus();
    }
}

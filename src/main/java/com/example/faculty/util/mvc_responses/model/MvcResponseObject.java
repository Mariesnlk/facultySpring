package com.example.faculty.util.mvc_responses.model;

import com.example.faculty.util.mvc_responses.resources.Statuses;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


public class MvcResponseObject extends MvcResponse {
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("response")
    private Object response;

    public MvcResponseObject(int status, Object response) {
        super(status);
        this.response = response;
    }

    public MvcResponseObject(Statuses status, Object response) {
        super(status.ordinal());
        this.response = response;
    }

    @Override
    public String toString() {
        return "{" +
                "\"response\":" + response +
                ", \"status\":" + status +
                '}';
    }
}

package com.example.faculty.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.FOUND)
public class ResourceFoundException extends RuntimeException {
    private final String resourceName;
    private final Object fieldName;
    private final Object fieldValue;
    private final Object typeValue;

    public ResourceFoundException(String resourceName, Object fieldName, Object fieldValue, Object typeValue) {
        super(String.format("%s found with '%s': '%s' and '%s'", resourceName, fieldName, fieldValue, typeValue), null, false, false);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.typeValue = typeValue;
    }
}
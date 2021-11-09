package com.example.faculty.exception;

public class NotEnoughRightsException extends  CustomRuntimeException {
    public NotEnoughRightsException() {
        super("Not enough rights exception");
    }
}

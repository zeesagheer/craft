package com.intuit.craft.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@ToString
@Getter
public enum ResponseCode {
    VALIDATION_TASK_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "Profile with id = %s not found");

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

}
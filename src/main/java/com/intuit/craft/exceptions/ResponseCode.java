package com.intuit.craft.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@ToString
@Getter
public enum ResponseCode {
    REQUEST_FLOODING(8000, HttpStatus.INTERNAL_SERVER_ERROR, "Multiple request with same data. Please try again!");

    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

}
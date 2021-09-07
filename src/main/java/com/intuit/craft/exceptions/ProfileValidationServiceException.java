package com.intuit.craft.exceptions;


import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ProfileValidationServiceException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private Throwable messageTrace;


    /**
     * @param errorCode    : Code for the exception.
     * @param errorMessage : Error message.
     */
    public ProfileValidationServiceException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public ProfileValidationServiceException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.code = errorCode;
        this.message = errorMessage;
        this.httpStatus = httpStatus;
    }

    public ProfileValidationServiceException(ResponseCode responseCode, Object... arguments) {
        this(String.valueOf(responseCode.getCode())
                , String.format(responseCode.getDescription(), arguments)
                , responseCode.getHttpStatus());
    }


    public ProfileValidationServiceException(ResponseCode responseCode) {
        this(String.valueOf(responseCode.getCode())
                , responseCode.getDescription()
                , responseCode.getHttpStatus());
    }
}

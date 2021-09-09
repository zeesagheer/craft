package com.intuit.craft.exceptions;


import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * Profile validation service exception.
 */
@Getter
@ToString
public class ProfileValidationServiceException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private Throwable messageTrace;


    /**
     * Instantiates a new Profile validation service exception.
     *
     * @param errorCode    : Code for the exception.
     * @param errorMessage : Error message.
     */
    public ProfileValidationServiceException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    /**
     * Instantiates a new Profile validation service exception.
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @param httpStatus   the http status
     */
    public ProfileValidationServiceException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.code = errorCode;
        this.message = errorMessage;
        this.httpStatus = httpStatus;
    }

    /**
     * Instantiates a new Profile validation service exception.
     *
     * @param responseCode the response code
     * @param arguments    the arguments
     */
    public ProfileValidationServiceException(ResponseCode responseCode, Object... arguments) {
        this(String.valueOf(responseCode.getCode())
                , String.format(responseCode.getDescription(), arguments)
                , responseCode.getHttpStatus());
    }


    /**
     * Instantiates a new Profile validation service exception.
     *
     * @param responseCode the response code
     */
    public ProfileValidationServiceException(ResponseCode responseCode) {
        this(String.valueOf(responseCode.getCode())
                , responseCode.getDescription()
                , responseCode.getHttpStatus());
    }
}

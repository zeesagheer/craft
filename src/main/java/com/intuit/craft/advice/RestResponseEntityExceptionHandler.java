package com.intuit.craft.advice;

import com.intuit.craft.exceptions.ProfileValidationServiceException;
import com.intuit.craft.utils.APIWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ProfileValidationServiceException.class})
    protected ResponseEntity<Object> handleProfileValidationException(ProfileValidationServiceException ex) {
        return APIWrapper.wrap(ex);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        return APIWrapper.wrap(ex);
    }

}

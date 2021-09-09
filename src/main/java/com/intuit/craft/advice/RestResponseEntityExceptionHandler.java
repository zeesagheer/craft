package com.intuit.craft.advice;

import com.intuit.craft.exceptions.ProfileValidationServiceException;
import com.intuit.craft.utils.APIWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Rest response entity exception handler.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    /**
     * Handle profile validation exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(value = {ProfileValidationServiceException.class})
    protected ResponseEntity<Object> handleProfileValidationException(ProfileValidationServiceException ex) {
        return APIWrapper.wrap(ex);
    }

    /**
     * Handle exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(Exception ex) {
        return APIWrapper.wrap(ex);
    }

}

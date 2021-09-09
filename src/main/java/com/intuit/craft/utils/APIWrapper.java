package com.intuit.craft.utils;

import com.intuit.craft.dto.AppAPIWrapper;
import com.intuit.craft.exceptions.ProfileValidationServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The type Api wrapper.
 */
public class APIWrapper {
    /**
     * Wrap response entity.
     *
     * @param <T>            the type parameter
     * @param code           the code
     * @param message        the message
     * @param responseEntity the response entity
     * @return the response entity
     */
    public static <T> ResponseEntity<AppAPIWrapper> wrap(Integer code, String message, ResponseEntity<T> responseEntity) {
        if (null == code) {
            code = responseEntity.getStatusCode().value();
        }
        if (null == message) {
            message = responseEntity.getStatusCode().getReasonPhrase();
        }
        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(new AppAPIWrapper(code, responseEntity.getBody(), message));
    }

    /**
     * Wrap response entity.
     *
     * @param <T>  the type parameter
     * @param body the body
     * @return the response entity
     */
    public static <T> ResponseEntity<AppAPIWrapper> wrap(ResponseEntity<T> body) {
        return wrap(null, null, body);
    }

    /**
     * Wrap response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    public static ResponseEntity<Object> wrap(ProfileValidationServiceException ex) {
        int code = 200;
        if (null != ex.getCode()) {
            code = Integer.parseInt(ex.getCode());
        } else if (null != ex.getHttpStatus()) {
            code = ex.getHttpStatus().value();
        }
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (null != ex.getHttpStatus()) {
            httpStatus = ex.getHttpStatus();
        }
        return ResponseEntity
                .status(httpStatus)
                .body(new AppAPIWrapper(code, null, ex.getMessage()));
    }

    /**
     * Wrap response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    public static ResponseEntity<Object> wrap(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(httpStatus)
                .body(new AppAPIWrapper(httpStatus.value(), null, ex.getMessage()));
    }
}

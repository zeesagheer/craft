package com.intuit.craft.utils;

import com.intuit.craft.dto.AppAPIWrapper;
import com.intuit.craft.exceptions.ProfileValidationServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIWrapper {
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

    public static <T> ResponseEntity<AppAPIWrapper> wrap(ResponseEntity<T> body) {
        return wrap(null, null, body);
    }

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

    public static ResponseEntity<Object> wrap(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(httpStatus)
                .body(new AppAPIWrapper(httpStatus.value(), null, ex.getMessage()));
    }
}

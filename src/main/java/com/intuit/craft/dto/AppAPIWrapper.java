package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * API wrapper to wrap default body around any data
 */
@Data
@AllArgsConstructor
public class AppAPIWrapper {
    private int code;
    private Object data;
    private String message;
}

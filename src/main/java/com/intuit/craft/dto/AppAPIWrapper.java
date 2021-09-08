package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppAPIWrapper {
    private int code;
    private Object data;
    private String message;
}

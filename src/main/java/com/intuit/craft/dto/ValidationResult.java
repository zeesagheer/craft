package com.intuit.craft.dto;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResult {
    private ValidationTaskStatus status;
    private String displayStatus;
    private List<String> reasons;
}

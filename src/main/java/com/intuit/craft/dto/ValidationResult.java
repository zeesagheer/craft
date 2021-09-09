package com.intuit.craft.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The type Validation result.
 */
@Data
public class ValidationResult implements Serializable {
    private ValidationTaskStatus status;
    private String displayStatus;
    private List<String> reasons;
}

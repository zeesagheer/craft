package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Sub validation result.
 */
@Data
@AllArgsConstructor
public class SubValidationResult {
    /**
     * The Status.
     */
    ValidationTaskSubStatus status;
    /**
     * The Reason.
     */
    String reason;
}

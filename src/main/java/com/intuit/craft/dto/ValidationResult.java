package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationResult {
    ValidationTaskSubStatus status;
    String reason;
}

package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubValidationResult {
    ValidationTaskSubStatus status;
    String reason;
}

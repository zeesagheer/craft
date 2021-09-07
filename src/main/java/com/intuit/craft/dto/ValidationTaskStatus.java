package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
public enum ValidationTaskStatus {
    PENDING("Pending"),
    NO_SUBSCRIPTIONS("No Subscriptions"),
    IN_PROGRESS("In Progress"),
    VALID("Validation successful"),
    INVALID("Validation failed");

    private final String displayStatus;
}

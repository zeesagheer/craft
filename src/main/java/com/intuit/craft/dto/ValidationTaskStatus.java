package com.intuit.craft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Validation task status.
 */
@AllArgsConstructor
@Getter
public enum ValidationTaskStatus {
    /**
     * Pending validation task status.
     */
    PENDING("Pending"),
    /**
     * The No subscriptions.
     */
    NO_SUBSCRIPTIONS("No Subscriptions"),
    /**
     * The In progress.
     */
    IN_PROGRESS("In Progress"),
    /**
     * The Valid.
     */
    VALID("Validation successful"),
    /**
     * The Invalid.
     */
    INVALID("Validation failed");

    private final String displayStatus;
}

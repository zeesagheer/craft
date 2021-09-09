package com.intuit.craft.services;

import com.intuit.craft.dto.ValidationResult;

/**
 * Task status service, provides status for submitted task.
 */
public interface TaskStatusService {

    /**
     * Gets profile validation task status.
     *
     * @param requestId the request id
     * @return the profile validation task status
     */
    ValidationResult getProfileValidationTaskStatus(String requestId);
}

package com.intuit.craft.services;

import com.intuit.craft.entities.ProfileValidationTask;

/**
 * Profile validation service, used to validate {@link ProfileValidationTask}.
 */
public interface ProfileValidationService {
    /**
     * Validate.
     *
     * @param profileTask the profile task
     */
    void validate(ProfileValidationTask profileTask);
}

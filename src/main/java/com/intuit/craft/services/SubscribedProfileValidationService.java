package com.intuit.craft.services;

import com.intuit.craft.dto.SubValidationResult;
import com.intuit.craft.entities.ProfileValidationSubTask;

import java.net.SocketTimeoutException;

/**
 * Subscribed profile validation service, used to validate {@link ProfileValidationSubTask}.
 */
public interface SubscribedProfileValidationService {
    /**
     * Validate sub validation result.
     *
     * @param profileValidationSubTask the profile validation sub task
     * @return the sub validation result
     * @throws SocketTimeoutException the socket timeout exception
     */
    SubValidationResult validate(ProfileValidationSubTask profileValidationSubTask) throws SocketTimeoutException;
}

package com.intuit.craft.services;

import com.intuit.craft.dto.request.ProfileRequest;

/**
 * Task acceptor service used to submit and create profile validation task.
 */
public interface TaskAcceptorService {
    /**
     * Create profile validation task string.
     *
     * @param request the request
     * @return the string
     */
    String createProfileValidationTask(ProfileRequest request);
}

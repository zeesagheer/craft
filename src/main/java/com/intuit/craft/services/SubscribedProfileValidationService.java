package com.intuit.craft.services;

import com.intuit.craft.entities.ProfileValidationSubTask;

import java.net.SocketTimeoutException;

public interface SubscribedProfileValidationService {
    Boolean validate(ProfileValidationSubTask profileValidationSubTask) throws SocketTimeoutException;
}

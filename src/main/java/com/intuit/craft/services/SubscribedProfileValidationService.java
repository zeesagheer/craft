package com.intuit.craft.services;

import com.intuit.craft.dto.ValidationResult;
import com.intuit.craft.entities.ProfileValidationSubTask;

import java.net.SocketTimeoutException;

public interface SubscribedProfileValidationService {
    ValidationResult validate(ProfileValidationSubTask profileValidationSubTask) throws SocketTimeoutException;
}

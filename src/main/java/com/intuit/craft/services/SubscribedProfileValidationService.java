package com.intuit.craft.services;

import com.intuit.craft.dto.SubValidationResult;
import com.intuit.craft.entities.ProfileValidationSubTask;

import java.net.SocketTimeoutException;

public interface SubscribedProfileValidationService {
    SubValidationResult validate(ProfileValidationSubTask profileValidationSubTask) throws SocketTimeoutException;
}

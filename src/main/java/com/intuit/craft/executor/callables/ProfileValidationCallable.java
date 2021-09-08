package com.intuit.craft.executor.callables;

import com.intuit.craft.dto.ValidationResult;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.services.SubscribedProfileValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

@AllArgsConstructor
@Slf4j
public class ProfileValidationCallable implements Callable<ValidationResult> {

    private final ProfileValidationSubTask profileValidationSubTask;
    private final SubscribedProfileValidationService subscribedProfileValidationService;

    @Override
    public ValidationResult call() {
        ValidationResult validationResult = null;
        try {
            validationResult = subscribedProfileValidationService.validate(profileValidationSubTask);
        } catch (SocketTimeoutException e) {
            log.error("Exception while validating subTask {}", profileValidationSubTask, e);
        }
        return validationResult;
    }
}

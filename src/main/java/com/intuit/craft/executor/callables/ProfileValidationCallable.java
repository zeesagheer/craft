package com.intuit.craft.executor.callables;

import com.intuit.craft.dto.SubValidationResult;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.services.SubscribedProfileValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;

/**
 * Profile validation callable used to call different validation services.
 */
@AllArgsConstructor
@Slf4j
public class ProfileValidationCallable implements Callable<SubValidationResult> {

    private final ProfileValidationSubTask profileValidationSubTask;
    private final SubscribedProfileValidationService subscribedProfileValidationService;

    @Override
    public SubValidationResult call() {
        SubValidationResult subValidationResult = null;
        try {
            subValidationResult = subscribedProfileValidationService.validate(profileValidationSubTask);
        } catch (SocketTimeoutException e) {
            log.error("Exception occurred while validating subTask {}", profileValidationSubTask, e);
        }
        return subValidationResult;
    }
}

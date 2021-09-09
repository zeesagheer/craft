package com.intuit.craft.services.impl;

import com.intuit.craft.dto.ValidationResult;
import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.exceptions.ProfileValidationServiceException;
import com.intuit.craft.exceptions.ResponseCode;
import com.intuit.craft.repositories.ProfileValidationSubTaskRepository;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Task status service.
 */
@Service
@AllArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

    private final ProfileValidationTaskRepository profileValidationTaskRepository;
    private final ProfileValidationSubTaskRepository profileValidationSubTaskRepository;

    @Override
    @Cacheable(value = "calculatedStatusCache", unless = "#result.status.name == \"IN_PROGRESS\"")
    public ValidationResult getProfileValidationTaskStatus(String requestId) {
        Optional<ProfileValidationTask> profile = profileValidationTaskRepository.findById(requestId);
        ValidationTaskStatus taskStatus = profile.orElseThrow(() -> new ProfileValidationServiceException(ResponseCode.VALIDATION_TASK_NOT_FOUND, requestId))
                .getStatus();
        ValidationResult validationResult = new ValidationResult();
        validationResult.setStatus(taskStatus);
        validationResult.setDisplayStatus(taskStatus.getDisplayStatus());
        if (ValidationTaskStatus.INVALID.equals(taskStatus)) {
            validationResult.setReasons(profileValidationSubTaskRepository.findAllByParentTaskId(requestId).stream()
                    .filter(subTask -> ValidationTaskSubStatus.INVALID.equals(subTask.getStatus()))
                    .map(ProfileValidationSubTask::getReason).filter(Objects::nonNull)
                    .collect(Collectors.toList()));
        }
        return validationResult;
    }
}

package com.intuit.craft.services.impl;

import com.intuit.craft.convertor.Convertor;
import com.intuit.craft.dto.ValidationResult;
import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.dto.request.ProfileRequest;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.exceptions.ProfileValidationServiceException;
import com.intuit.craft.exceptions.ResponseCode;
import com.intuit.craft.repositories.ProfileValidationSubTaskRepository;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.ProfileValidationService;
import com.intuit.craft.services.TaskAcceptorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskAcceptorServiceImpl implements TaskAcceptorService {

    private final ProfileValidationTaskRepository profileValidationTaskRepository;
    private final ProfileValidationSubTaskRepository profileValidationSubTaskRepository;
    private final Convertor<ProfileRequest, ProfileValidationTask> profileValidationTaskConvertor;
    private final ProfileValidationService profileValidationService;
    private final ExecutorService executorService = Executors.newWorkStealingPool();

    @Override
    public String createProfileValidationTask(ProfileRequest request) {
        ProfileValidationTask profileTask = profileValidationTaskConvertor.convert(request);
        profileValidationTaskRepository.save(profileTask);
        executorService.execute(() -> profileValidationService.validate(profileTask));
        return profileTask.getId();
    }

    @Override
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

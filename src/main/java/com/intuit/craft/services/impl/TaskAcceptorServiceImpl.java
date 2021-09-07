package com.intuit.craft.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.craft.builder.ProfileValidationTaskBuilder;
import com.intuit.craft.dto.request.ProfileRequest;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.ProfileValidationService;
import com.intuit.craft.services.TaskAcceptorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskAcceptorServiceImpl implements TaskAcceptorService {

    private final ProfileValidationTaskRepository profileValidationTaskRepository;
    private final ProfileValidationTaskBuilder profileValidationTaskBuilder;
    private final ProfileValidationService profileValidationService;
    private final ExecutorService executorService = Executors.newWorkStealingPool();

    @Override
    public String createProfileValidationTask(ProfileRequest request) {
        try {
            ProfileValidationTask profileTask = profileValidationTaskBuilder.build(request);
            profileValidationTaskRepository.save(profileTask);
            executorService.execute(() -> profileValidationService.validate(profileTask));
            return profileTask.getId();
        } catch (JsonProcessingException e) {
            log.error("Error while parsing request {} ", request, e);
            throw new RuntimeException("Error while parsing request");
        }
    }

    @Override
    public String getProfileValidationTaskStatus(String requestId) {
        Optional<ProfileValidationTask> profile = profileValidationTaskRepository.findById(requestId);
        return profile.orElseThrow(() -> new RuntimeException("Not found")).getStatus().getDisplayStatus();
    }
}

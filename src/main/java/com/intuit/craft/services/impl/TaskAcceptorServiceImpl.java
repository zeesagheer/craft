package com.intuit.craft.services.impl;

import com.intuit.craft.convertor.Convertor;
import com.intuit.craft.dto.request.ProfileRequest;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.ProfileValidationService;
import com.intuit.craft.services.TaskAcceptorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Task acceptor service implementation.
 */
@Service
@AllArgsConstructor
public class TaskAcceptorServiceImpl implements TaskAcceptorService {

    private final ProfileValidationTaskRepository profileValidationTaskRepository;
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

}

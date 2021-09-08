package com.intuit.craft.services.impl;

import com.intuit.craft.convertor.Convertor;
import com.intuit.craft.dto.UserSubscription;
import com.intuit.craft.dto.SubValidationResult;
import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.executor.callables.ProfileValidationCallable;
import com.intuit.craft.repositories.ProfileValidationSubTaskRepository;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.ProfileValidationService;
import com.intuit.craft.services.SubscribedProfileValidationService;
import com.intuit.craft.services.UserSubscriptionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileValidationServiceImpl implements ProfileValidationService {
    private final ProfileValidationTaskRepository profileValidationTaskRepository;
    private final ProfileValidationSubTaskRepository profileValidationSubTaskRepository;
    private final UserSubscriptionService userSubscriptionService;
    private final Convertor<UserSubscription, ProfileValidationSubTask> profileValidationSubTaskConvertor;
    private final ExecutorService executorService = Executors.newWorkStealingPool();
    private final SubscribedProfileValidationService subscribedProfileValidationService;

    @Override
    public void validate(ProfileValidationTask profileTask) {
        List<ProfileValidationSubTask> profileValidationSubTaskList = null;
        if (ValidationTaskStatus.PENDING.equals(profileTask.getStatus())) {
            List<UserSubscription> subscriptionsList = userSubscriptionService.getSubscriptions(profileTask.getId());
            if (subscriptionsList.isEmpty()) {
                log.info("User profile {} is not registered with any subscriptions", profileTask.getProfileId());
                profileTask.setStatus(ValidationTaskStatus.NO_SUBSCRIPTIONS);
                profileValidationTaskRepository.save(profileTask);
                return;
            }
            profileValidationSubTaskList = profileValidationSubTaskConvertor.convert(subscriptionsList);
            profileValidationSubTaskList.forEach(item -> item.setParentTaskId(profileTask.getId()));
            profileValidationSubTaskRepository.saveAll(profileValidationSubTaskList);
            profileTask.setStatus(ValidationTaskStatus.IN_PROGRESS);
            profileValidationTaskRepository.save(profileTask);
        }

        if (ValidationTaskStatus.IN_PROGRESS.equals(profileTask.getStatus())) {
            if (null == profileValidationSubTaskList) {
                profileValidationSubTaskList = profileValidationSubTaskRepository
                        .findAllByParentTaskId(profileTask.getProfileId());
            }
            try {
                List<Future<SubValidationResult>> futures = executorService.invokeAll(
                        profileValidationSubTaskList.stream()
                                .map(p -> new ProfileValidationCallable(p, subscribedProfileValidationService))
                                .collect(Collectors.toList())
                        , 30000, TimeUnit.MILLISECONDS);
                List<SubValidationResult> listResult = futures.stream().map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("Error while validating sub task", e);
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toList());
                if (listResult.size() == futures.size()) {
                    profileTask.setStatus(listResult.stream().anyMatch(validationResult
                            -> ValidationTaskSubStatus.INVALID.equals(validationResult.getStatus()))
                            ? ValidationTaskStatus.INVALID : ValidationTaskStatus.VALID);
                    profileValidationTaskRepository.save(profileTask);
                    log.info("Marking task {} for profile {} as {}", profileTask.getId(), profileTask.getProfileId()
                            , profileTask.getStatus());
                }
            } catch (InterruptedException e) {
                log.error("Error while validating task {}", profileTask, e);
            }
        } else {
            log.info("Profile task {} is already in {}", profileTask.getId(), profileTask.getStatus());
        }
    }
}

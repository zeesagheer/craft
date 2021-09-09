package com.intuit.craft.scheduler;

import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.ProfileValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Failed profile validations scheduler, it runs using configured cron expression
 * to retry the failed subtasks.
 */
@Slf4j
@Service
@AllArgsConstructor
public class FailedProfileValidationsScheduler {

    private final ProfileValidationTaskRepository profileValidationTaskRepository;
    private final ProfileValidationService profileValidationService;
    private final List<ValidationTaskStatus> statusList = Arrays.asList(ValidationTaskStatus.PENDING, ValidationTaskStatus.IN_PROGRESS);

    @Value("${failed.task.scheduler.delay.time}")
    private final List<Long> delaySeconds;

    /**
     * Execute.
     */
    @Scheduled(cron = "${failed.task.scheduler.cron.regex}")
    public void execute() {
        log.info("Initiating failed tasks scheduler");
        Calendar currentTime = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DATE, -2);

        Calendar end = Calendar.getInstance();
        end.add(Calendar.SECOND, -5);

        List<ProfileValidationTask> tasks = profileValidationTaskRepository
                .findAllByStatusInAndCreatedDateBetweenAndRetryCountLessThan(statusList, start.getTime(), end.getTime(), delaySeconds.size())
                .stream().filter(task -> currentTime.getTimeInMillis() - task.getCreatedDate().getTime() >= 1000 * delaySeconds.get(task.getRetryCount()))
                .limit(1000).collect(Collectors.toList());

        tasks.forEach(task -> task.setRetryCount(task.getRetryCount() + 1));
        profileValidationTaskRepository.saveAll(tasks);

        tasks.parallelStream().forEach(task -> {
            profileValidationService.validate(task);
            log.info("Submitted {} via scheduler", task.getId());
        });
    }
}

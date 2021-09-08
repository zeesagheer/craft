package com.intuit.craft.services.impl;

import com.intuit.craft.dto.SubValidationResult;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.repositories.ProfileValidationSubTaskRepository;
import com.intuit.craft.services.SubscribedProfileValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SubscribedProfileValidationServiceImpl implements SubscribedProfileValidationService {
    private final ProfileValidationSubTaskRepository profileValidationSubTaskRepository;
    private final List<String> randomReason = Arrays.asList(
            "pincode is wrong in address"
            , "Name has speacial character"
            , "tax info is incorrect");

    @Override
    public SubValidationResult validate(ProfileValidationSubTask profileValidationSubTask) throws SocketTimeoutException {
        sleepForOneSecond();
        if (!getRandomTrueWithPercent(90)) {
            throw new SocketTimeoutException();
        }
        Boolean result = getRandomTrueWithPercent(80);
        profileValidationSubTask.setStatus(result ? ValidationTaskSubStatus.VALID : ValidationTaskSubStatus.INVALID);
        if (!result) {
            profileValidationSubTask.setReason(getRandomReason());
        }

        profileValidationSubTaskRepository.save(profileValidationSubTask);
        log.info("Subtask id {} status is {}", profileValidationSubTask.getId(), profileValidationSubTask.getStatus());
        return new SubValidationResult(profileValidationSubTask.getStatus(), profileValidationSubTask.getReason());
    }

    private String getRandomReason() {
        return randomReason.get(((int) (Math.random() * 100)) % randomReason.size());
    }

    private Boolean getRandomTrueWithPercent(int percent) {
        return Math.random() * 100 < percent;
    }

    private void sleepForOneSecond() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

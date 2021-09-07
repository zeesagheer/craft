package com.intuit.craft.builder;

import com.intuit.craft.dto.UserSubscription;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidationSubTaskBuilder {

    public ProfileValidationSubTask build(UserSubscription subscription) {
        ProfileValidationSubTask profileValidationSubTask = new ProfileValidationSubTask();
        profileValidationSubTask.setServiceId(subscription.getId());
        profileValidationSubTask.setStatus(ValidationTaskSubStatus.PENDING);
        return profileValidationSubTask;
    }
}

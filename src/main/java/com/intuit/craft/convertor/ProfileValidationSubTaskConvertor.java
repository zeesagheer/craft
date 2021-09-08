package com.intuit.craft.convertor;

import com.intuit.craft.dto.UserSubscription;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidationSubTaskConvertor extends GenericAbstractConvertor<UserSubscription, ProfileValidationSubTask> {

    public ProfileValidationSubTask convert(UserSubscription subscription) {
        ProfileValidationSubTask profileValidationSubTask = new ProfileValidationSubTask();
        profileValidationSubTask.setServiceId(subscription.getId());
        profileValidationSubTask.setStatus(ValidationTaskSubStatus.PENDING);
        return profileValidationSubTask;
    }
}

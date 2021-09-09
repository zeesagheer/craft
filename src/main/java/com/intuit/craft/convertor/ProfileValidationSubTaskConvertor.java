package com.intuit.craft.convertor;

import com.intuit.craft.dto.UserSubscription;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import org.springframework.stereotype.Component;

/**
 * The type Profile validation sub task convertor converting
 * from {@link UserSubscription} to {@link ProfileValidationSubTask}.
 */
@Component
public class ProfileValidationSubTaskConvertor extends GenericAbstractConvertor<UserSubscription, ProfileValidationSubTask> {

    public ProfileValidationSubTask convert(UserSubscription subscription) {
        ProfileValidationSubTask profileValidationSubTask = new ProfileValidationSubTask();
        profileValidationSubTask.setServiceId(subscription.getId());
        profileValidationSubTask.setStatus(ValidationTaskSubStatus.PENDING);
        return profileValidationSubTask;
    }
}

package com.intuit.craft.convertor;

import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.dto.request.ProfileRequest;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.utils.JacksonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileValidationTaskConvertor extends GenericAbstractConvertor<ProfileRequest, ProfileValidationTask> {

    private final JacksonUtils jacksonUtils;

    public ProfileValidationTask convert(ProfileRequest request) {
        ProfileValidationTask profileValidationTask = new ProfileValidationTask();
        profileValidationTask.setProfileId(request.getProfileId());
        profileValidationTask.setStatus(ValidationTaskStatus.PENDING);
        profileValidationTask.setRetryCount(0);
        profileValidationTask.setProfileRequest(jacksonUtils.objectToJsonStr(request));
        return profileValidationTask;
    }
}

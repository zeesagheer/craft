package com.intuit.craft.services;

import com.intuit.craft.dto.request.ProfileRequest;

public interface TaskAcceptorService {
    String createProfileValidationTask(ProfileRequest request);

    String getProfileValidationTaskStatus(String requestId);
}

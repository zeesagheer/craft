package com.intuit.craft.controllers;

import com.intuit.craft.dto.AppAPIWrapper;
import com.intuit.craft.services.TaskStatusService;
import com.intuit.craft.utils.APIWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for showing task status
 */
@RestController
@AllArgsConstructor
public class TaskStatusController {
    private final TaskStatusService taskStatusService;

    /**
     * Update profile response entity.
     *
     * @param requestId the request id
     * @return the response entity
     */
    @GetMapping(value = "/task/{requestId}/status", produces = {"application/json"}
            , name = "Fetch status of profile validation task")
    public ResponseEntity<AppAPIWrapper> updateProfile(@PathVariable String requestId) {
        return APIWrapper.wrap(ResponseEntity.ok().body(taskStatusService.getProfileValidationTaskStatus(requestId)));
    }

}

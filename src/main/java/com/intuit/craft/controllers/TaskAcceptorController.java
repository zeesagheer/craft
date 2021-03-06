package com.intuit.craft.controllers;

import com.intuit.craft.dto.AppAPIWrapper;
import com.intuit.craft.dto.request.ProfileRequest;
import com.intuit.craft.services.TaskAcceptorService;
import com.intuit.craft.utils.APIWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller accepts the validation profile tasks submitted by user
 */
@RestController
@AllArgsConstructor
public class TaskAcceptorController {

    private final TaskAcceptorService taskAcceptorService;

    /**
     * Update profile response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(value = "/task", consumes = {"application/json"}, produces = {"application/json"}
            , name = "Validates user profile")
    public ResponseEntity<AppAPIWrapper> updateProfile(@RequestBody ProfileRequest request) {
        return APIWrapper.wrap(ResponseEntity.accepted().body(taskAcceptorService.createProfileValidationTask(request)));
    }

}

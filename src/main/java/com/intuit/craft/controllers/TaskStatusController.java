package com.intuit.craft.controllers;

import com.intuit.craft.dto.AppAPIWrapper;
import com.intuit.craft.services.TaskAcceptorService;
import com.intuit.craft.utils.APIWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TaskStatusController {
    private final TaskAcceptorService taskAcceptorService;

    @GetMapping(value = "/task/status", produces = {"application/json"}
            , name = "Fetch status of profile validation task")
    public ResponseEntity<AppAPIWrapper> updateProfile(@RequestParam String requestId) {
        return APIWrapper.wrap(ResponseEntity.ok().body(taskAcceptorService.getProfileValidationTaskStatus(requestId)));
    }

}

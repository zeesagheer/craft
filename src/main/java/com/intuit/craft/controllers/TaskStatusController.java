package com.intuit.craft.controllers;

import com.intuit.craft.services.TaskAcceptorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskStatusController {
    private final TaskAcceptorService taskAcceptorService;

    @GetMapping(value = "/task/status", produces = {"application/json"}
            , name = "Fetch status of profile validation task")
    public ResponseEntity<Object> updateProfile(@RequestParam String requestId) {
        return ResponseEntity.ok().body(taskAcceptorService.getProfileValidationTaskStatus(requestId));
    }

}

package com.intuit.craft.controllers;

import com.intuit.craft.dto.request.ProfileRequest;
import com.intuit.craft.services.TaskAcceptorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskAcceptorController {

    private final TaskAcceptorService taskAcceptorService;

    @PostMapping(value = "/task", consumes = {"application/json"}, produces = {"application/json"}
            , name = "Validates user profile")
    public ResponseEntity<Object> updateProfile(@RequestBody ProfileRequest request) {
        return ResponseEntity.accepted().body(taskAcceptorService.createProfileValidationTask(request));
    }

}

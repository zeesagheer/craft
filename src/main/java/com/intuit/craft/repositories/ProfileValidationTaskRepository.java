package com.intuit.craft.repositories;

import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.entities.ProfileValidationTask;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

@EnableScan
public interface ProfileValidationTaskRepository extends CrudRepository<ProfileValidationTask, String> {
    List<ProfileValidationTask> findAllByStatusInAndCreatedDateBetweenAndRetryCountLessThan(List<ValidationTaskStatus> statuses, Date start, Date end, Integer retryCount);
}

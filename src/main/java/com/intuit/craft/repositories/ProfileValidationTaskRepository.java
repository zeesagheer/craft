package com.intuit.craft.repositories;

import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.entities.ProfileValidationTask;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Profile validation task repository.
 */
@EnableScan
public interface ProfileValidationTaskRepository extends CrudRepository<ProfileValidationTask, String> {
    /**
     * Find all by status in and created date between and retry count less than list.
     *
     * @param statuses   {@link List} of {@link ValidationTaskStatus}
     * @param start      the start
     * @param end        the end
     * @param retryCount the retry count
     * @return the list
     */
    List<ProfileValidationTask> findAllByStatusInAndCreatedDateBetweenAndRetryCountLessThan(List<ValidationTaskStatus> statuses, Date start, Date end, Integer retryCount);
}

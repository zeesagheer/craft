package com.intuit.craft.repositories;

import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.entities.ProfileValidationTask;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Profile validation sub task repository.
 */
@EnableScan
public interface ProfileValidationSubTaskRepository extends CrudRepository<ProfileValidationSubTask, String> {
    /**
     * Find all by parent task id list.
     *
     * @param parentId the {@link ProfileValidationTask#getId()} of parent task
     * @return the {@link List} of {@link ProfileValidationSubTask}
     */
    List<ProfileValidationSubTask> findAllByParentTaskId(String parentId);
}

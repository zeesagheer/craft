package com.intuit.craft.repositories;

import com.intuit.craft.entities.ProfileValidationSubTask;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ProfileValidationSubTaskRepository extends CrudRepository<ProfileValidationSubTask, String> {
    List<ProfileValidationSubTask> findAllByParentTaskId(String parentId);
}

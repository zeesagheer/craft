package com.intuit.craft.services;


import com.intuit.craft.convertor.ProfileValidationSubTaskConvertor;
import com.intuit.craft.dto.UserSubscription;
import com.intuit.craft.dto.SubValidationResult;
import com.intuit.craft.dto.ValidationTaskStatus;
import com.intuit.craft.dto.ValidationTaskSubStatus;
import com.intuit.craft.entities.ProfileValidationSubTask;
import com.intuit.craft.entities.ProfileValidationTask;
import com.intuit.craft.repositories.ProfileValidationSubTaskRepository;
import com.intuit.craft.repositories.ProfileValidationTaskRepository;
import com.intuit.craft.services.impl.ProfileValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ProfileValidationServiceTest {

    @TestConfiguration
    static class ProfileValidationServiceTestContextConfiguration {
        @Bean
        public ProfileValidationSubTaskConvertor getProfileValidationSubTaskBuilder() {
            return new ProfileValidationSubTaskConvertor();
        }
    }

    @Mock
    private ProfileValidationTaskRepository profileValidationTaskRepository;

    @Mock
    private ProfileValidationSubTaskRepository profileValidationSubTaskRepository;

    @Mock
    private UserSubscriptionService userSubscriptionService;

    @Autowired
    @Spy
    private ProfileValidationSubTaskConvertor profileValidationSubTaskConvertor;


    @Mock
    private SubscribedProfileValidationService subscribedProfileValidationService;

    @InjectMocks
    private ProfileValidationServiceImpl profileValidationService;

    @BeforeEach
    void init() {
        when(profileValidationTaskRepository.save(any(ProfileValidationTask.class))).then(returnsFirstArg());
    }

    @Test
    void profile_not_having_subscriptions() {
        ProfileValidationTask profileTask = getProfile();

        when(userSubscriptionService.getSubscriptions(any(String.class))).thenReturn(new ArrayList<>());
        profileValidationService.validate(profileTask);

        assertThat(profileTask.getStatus()).isEqualTo(ValidationTaskStatus.NO_SUBSCRIPTIONS);
    }

    @Test
    void profile_with_subscriptions_all_sub_validation_services_failed() {
        ProfileValidationTask profileTask = getProfile();
        List<UserSubscription> subscriptions = new ArrayList<>();

        subscriptions.add(new UserSubscription("QB-Accounting", "service1"));
        subscriptions.add(new UserSubscription("QB-Payments", "service3"));

        when(userSubscriptionService.getSubscriptions(any(String.class))).thenReturn(subscriptions);

        profileValidationService.validate(profileTask);

        assertThat(profileTask.getStatus()).isEqualTo(ValidationTaskStatus.IN_PROGRESS);
    }

    @Test
    void profile_with_subscriptions_one_validation_service_is_valid_and_rest_not_reachable() throws SocketTimeoutException {
        ProfileValidationTask profileTask = getProfile();
        List<UserSubscription> subscriptions = new ArrayList<>();

        subscriptions.add(new UserSubscription("QB-Accounting", "service1"));
        subscriptions.add(new UserSubscription("QB-Payments", "service3"));

        when(userSubscriptionService.getSubscriptions(any(String.class))).thenReturn(subscriptions);

        when(subscribedProfileValidationService.validate(any(ProfileValidationSubTask.class))).thenAnswer(answer -> {
            ProfileValidationSubTask task = answer.getArgument(0);
            if ("service1".equals(task.getServiceId())) {
                return new SubValidationResult(ValidationTaskSubStatus.VALID, "any random reason");
            }
            return null;
        });

        profileValidationService.validate(profileTask);

        assertThat(profileTask.getStatus()).isEqualTo(ValidationTaskStatus.IN_PROGRESS);
    }

    @Test
    void profile_with_subscriptions_one_validation_service_returned_invalid() throws SocketTimeoutException {
        ProfileValidationTask profileTask = getProfile();
        List<UserSubscription> subscriptions = new ArrayList<>();

        subscriptions.add(new UserSubscription("QB-Accounting", "service1"));
        subscriptions.add(new UserSubscription("QB-Payments", "service3"));

        when(userSubscriptionService.getSubscriptions(any(String.class))).thenReturn(subscriptions);

        when(subscribedProfileValidationService.validate(any(ProfileValidationSubTask.class))).thenAnswer(answer -> {
            ProfileValidationSubTask task = answer.getArgument(0);
            if ("service1".equals(task.getServiceId())) {
                return new SubValidationResult(ValidationTaskSubStatus.INVALID, "any random reason");
            } else {
                return new SubValidationResult(ValidationTaskSubStatus.VALID, null);
            }
        });

        profileValidationService.validate(profileTask);

        assertThat(profileTask.getStatus()).isEqualTo(ValidationTaskStatus.INVALID);
    }

    @Test
    void profile_with_subscriptions_all_validation_service_is_valid() throws SocketTimeoutException {
        ProfileValidationTask profileTask = getProfile();
        List<UserSubscription> subscriptions = new ArrayList<>();

        subscriptions.add(new UserSubscription("QB-Accounting", "service1"));
        subscriptions.add(new UserSubscription("QB-Payments", "service3"));

        when(userSubscriptionService.getSubscriptions(any(String.class))).thenReturn(subscriptions);

        when(subscribedProfileValidationService.validate(any(ProfileValidationSubTask.class)))
                .thenAnswer(answer -> new SubValidationResult(ValidationTaskSubStatus.VALID, null));

        profileValidationService.validate(profileTask);

        assertThat(profileTask.getStatus()).isEqualTo(ValidationTaskStatus.VALID);
    }

    private ProfileValidationTask getProfile() {
        ProfileValidationTask profileTask = new ProfileValidationTask();
        profileTask.setId("asdsa-dqd-dsds-dsds");
        profileTask.setRetryCount(0);
        profileTask.setStatus(ValidationTaskStatus.PENDING);
        profileTask.setProfileId("23232");
        profileTask.setCreatedDate(new Date());
        return profileTask;
    }

}

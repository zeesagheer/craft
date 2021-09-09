package com.intuit.craft.services;

import com.intuit.craft.dto.UserSubscription;

import java.util.List;

/**
 * User subscription service to fetch all subscriptions associated with profile.
 */
public interface UserSubscriptionService {
    /**
     * Gets subscriptions.
     *
     * @param profileId the profile id
     * @return the subscriptions
     */
    List<UserSubscription> getSubscriptions(String profileId);
}

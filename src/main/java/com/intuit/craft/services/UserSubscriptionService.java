package com.intuit.craft.services;

import com.intuit.craft.dto.UserSubscription;

import java.util.List;

public interface UserSubscriptionService {
    List<UserSubscription> getSubscriptions(String profileId);
}

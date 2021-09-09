package com.intuit.craft.services.impl;

import com.intuit.craft.dto.UserSubscription;
import com.intuit.craft.services.UserSubscriptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User subscription service implementation.
 */
@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    @Override
    public List<UserSubscription> getSubscriptions(String profileId) {
        List<UserSubscription> subscriptions = new ArrayList<>();
        if (randomBoolean()) {
            subscriptions.add(new UserSubscription("QB-Accounting", "service1"));
        }
        if (randomBoolean()) {
            subscriptions.add(new UserSubscription("QB-Payroll", "service2"));
        }
        if (randomBoolean()) {
            subscriptions.add(new UserSubscription("QB-Payments", "service3"));
        }
        if (randomBoolean()) {
            subscriptions.add(new UserSubscription("TSheets", "service4"));
        }
        return subscriptions;
    }

    private boolean randomBoolean() {
        return (int) (Math.random() * 100) % 2 == 0;
    }
}

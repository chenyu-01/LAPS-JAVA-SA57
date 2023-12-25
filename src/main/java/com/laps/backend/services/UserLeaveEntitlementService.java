package com.laps.backend.services;

import com.laps.backend.models.User;
import com.laps.backend.models.UserLeaveEntitlement;

public interface UserLeaveEntitlementService {
    UserLeaveEntitlement findByUserId(Long userId);

    UserLeaveEntitlement save(UserLeaveEntitlement newEntitlement);
}

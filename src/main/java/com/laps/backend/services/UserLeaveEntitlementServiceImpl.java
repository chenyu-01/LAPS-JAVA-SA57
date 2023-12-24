package com.laps.backend.services;

import com.laps.backend.models.UserLeaveEntitlement;
import com.laps.backend.repositories.UserLeaveEntitlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLeaveEntitlementServiceImpl implements UserLeaveEntitlementService {
    private final UserLeaveEntitlementRepository userLeaveEntitlementRepository;


    @Autowired
    public UserLeaveEntitlementServiceImpl(UserLeaveEntitlementRepository userLeaveEntitlementRepository){
        this.userLeaveEntitlementRepository = userLeaveEntitlementRepository;
    }

    @Override
    public UserLeaveEntitlement findByUserId(Long userId) {
        return userLeaveEntitlementRepository.findByUserId(userId);
    }

    @Override
    public void save(UserLeaveEntitlement newEntitlement) {
        userLeaveEntitlementRepository.save(newEntitlement);
    }

}

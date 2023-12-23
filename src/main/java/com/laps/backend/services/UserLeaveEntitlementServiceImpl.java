package com.laps.backend.services;

import com.laps.backend.models.LeaveTypeEnum;
import com.laps.backend.models.User;
import com.laps.backend.models.UserLeaveEntitlement;
import com.laps.backend.repositories.LeaveTypeRepository;
import com.laps.backend.repositories.UserLeaveEntitlementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLeaveEntitlementServiceImpl implements UserLeaveEntitlementService {
    private final UserLeaveEntitlementRepository userLeaveEntitlementRepository;

    private final LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public UserLeaveEntitlementServiceImpl(UserLeaveEntitlementRepository userLeaveEntitlementRepository, LeaveTypeRepository leaveTypeRepository){
        this.userLeaveEntitlementRepository = userLeaveEntitlementRepository;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public UserLeaveEntitlement findByUserId(Long userId) {
        return userLeaveEntitlementRepository.findByUserId(userId);
    }

    @Override
    public void initUserLeaveEntitlement(User user) {

    }


}

package com.laps.backend.services;

import com.laps.backend.models.*;
import com.laps.backend.repositories.LeaveTypeRepository;
import com.laps.backend.repositories.ManagerRepository;
import com.laps.backend.repositories.UserLeaveEntitlementRepository;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserLeaveEntitlementRepository userLeaveEntitlementRepository;
    private final ManagerRepository manager_repository;

    private final LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserLeaveEntitlementRepository userLeaveEntitlementRepository, ManagerRepository manager_repository, LeaveTypeRepository leaveTypeRepository1) {
        this.repository = repository;
        this.userLeaveEntitlementRepository = userLeaveEntitlementRepository;
        this.manager_repository = manager_repository;
        this.leaveTypeRepository = leaveTypeRepository1;
    }

    @Override
    public User findByEmail(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public void save(User user) {
        String role = user.getRole();
        repository.save(user);
        UserLeaveEntitlement userLeaveEntitlement = userLeaveEntitlementRepository.findByUserId(user.getId());
        if (!role.equals("User") && userLeaveEntitlement == null) { // if user is not a normal user and user leave entitlement is not initialized
            UserLeaveEntitlement newEntitlement = new UserLeaveEntitlement();
            newEntitlement.setUser(user);
            leaveTypeRepository.findAll().stream().filter(leaveType ->
                            leaveType.getRoleName().equals(user.getRole()))
                    .forEach(leaveType -> {
                        int entitledDays = leaveType.getEntitledNum();
                        if(leaveType.getName().equals(LeaveTypeEnum.ANNUAL)){
                            newEntitlement.setAnnualEntitledDays(entitledDays);
                        } else if(leaveType.getName().equals(LeaveTypeEnum.MEDICAL)){
                            newEntitlement.setMedicalEntitledDays(entitledDays);
                        } else if(leaveType.getName().equals(LeaveTypeEnum.COMPENSATION)){
                            newEntitlement.setCompensationEntitledDays(entitledDays);
                        }
                    });
            userLeaveEntitlementRepository.save(newEntitlement);
        }
    }

    @Override
    public List<Employee> findAllEmployeeByManager(Manager manager) {
        return manager.getSubordinates();
    }


    @Override
    public Manager findManagerById(long id) {
        return manager_repository.findById(id);
    }
}

package com.laps.backend.services;

import com.laps.backend.models.*;
import com.laps.backend.repositories.EmployeeReposity;
import com.laps.backend.repositories.LeaveTypeRepository;
import com.laps.backend.repositories.ManagerRepository;
import com.laps.backend.repositories.UserRepository;
import com.laps.backend.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeReposity employeeRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Autowired
    private UserLeaveEntitlementServiceImpl userLeaveEntitlementRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User editUser(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User not found with id: " + user.getId());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        //user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        //same user?
        if (!Objects.equals(updatedUser.getId(), id)) {
            throw new IllegalArgumentException("User id mismatch");
        }

        String newRole = updatedUser.getRole();
        updatedUser.setPassword(user.getPassword());

        return switch (newRole) {
            case "Manager" -> {
                Manager manager = new Manager(updatedUser);
                deleteUser(id);
                yield managerRepository.save(manager);
            }
            case "Employee" -> {
                if (updatedUser instanceof Employee) {
                    deleteUser(id);
                    yield employeeRepository.save((Employee) updatedUser);
                }
                Employee employee = new Employee(updatedUser);
                deleteUser(id);
                yield employeeRepository.save(employee);
            }
            case "Admin" -> {
                Admin admin = new Admin(updatedUser);
                deleteUser(id);
                yield userRepository.save(admin);
            }
            default -> throw new IllegalStateException("Unexpected value: " + newRole);
        };
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User createUser(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        userRepository.save(newUser);
        setEntitlement(newUser);
        return newUser;
    }

    @Override
    public User createAdmin(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Admin admin = new Admin(newUser);
        userRepository.save(admin);
        setEntitlement(admin);
        return admin;
    }

    public Manager getManagerByName(String managerName) {
        return managerRepository.findByName(managerName);
    }

    @Override
    public User createEmployee(User newUser, Manager manager) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Employee employee = new Employee(newUser);
        employee.setManager(manager);
        employeeRepository.save(employee);
        setEntitlement(employee);
        return employee;
    }

    @Override
    public User createEmployee(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Employee employee = new Employee(newUser);
        employeeRepository.save(employee);
        setEntitlement(employee);
        return employee;
    }

    @Override
    public User createManager(User newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Manager manager = new Manager(newUser);
        managerRepository.save(manager);
        setEntitlement(manager);
        return manager;
    }

    public User getManagerByEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get().getManager();
        } else
            throw new IllegalArgumentException("Employee not found with id: " + id);

    }

    public void updateUser(User user) {
        if (user instanceof Employee)
        {
            //modify origin manager's subordinates
            Manager originManager = ((Employee) user).getManager();
            if (originManager != null) {
                originManager.getSubordinates().remove(user);
                managerRepository.save(originManager);
            }
            employeeRepository.save((Employee) user);
        }
        else {
            userRepository.save(user);
        }
    }

    @Override
    public List<User> searchUser(String[] keyword) {
        List<String> user_fields = Arrays.asList("name", "email", "role");
        Specification<User> spec = UserSpecification.byKeywords(user_fields, keyword);

        return userRepository.findAll(spec);
    }

    public User createManager(User newUser, Manager manager) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("User with email " + newUser.getEmail() + " already exists");
        }
        Manager newmanager = new Manager(newUser);
        newmanager.setManager(manager);
        managerRepository.save(newmanager);
        setEntitlement(newmanager);
        return newmanager;
    }

    private <T> void setEntitlement(T newUser) {
        UserLeaveEntitlement userLeaveEntitlement = new UserLeaveEntitlement();
        userLeaveEntitlement.setUser((User) newUser);
        String userRole = ((User) newUser).getRole() == "User" ? "Employee" : ((User) newUser).getRole();
        leaveTypeRepository.findAll().stream().filter(leaveType ->
                        leaveType.getRoleName().equals(userRole))
                .forEach(leaveType -> {
                    int entitledDays = leaveType.getEntitledNum();
                    if(leaveType.getName().equals(LeaveTypeEnum.ANNUAL)){
                        userLeaveEntitlement.setAnnualEntitledDays(entitledDays);
                    } else if(leaveType.getName().equals(LeaveTypeEnum.MEDICAL)){
                        userLeaveEntitlement.setMedicalEntitledDays(entitledDays);
                    } else if(leaveType.getName().equals(LeaveTypeEnum.COMPENSATION)){
                        userLeaveEntitlement.setCompensationEntitledDays(entitledDays);
                    }
                });
        userLeaveEntitlementRepository.save(userLeaveEntitlement);
        ((User) newUser).setUserLeaveEntitlement(userLeaveEntitlement);
    }
}
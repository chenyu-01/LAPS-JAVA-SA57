package com.laps.backend;

import com.laps.backend.models.Employee;
import com.laps.backend.models.LeaveApplication;
import com.laps.backend.models.UserLeaveEntitlement;
import com.laps.backend.repositories.LeaveApplicationRepository;
import com.laps.backend.services.LeaveApplicationServiceImpl;
import com.laps.backend.services.PublicHolidayService;
import com.laps.backend.services.UserLeaveEntitlementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LeaveApplicationServiceImplTest {

    @Mock
    private LeaveApplicationRepository leaveApplicationRepository;
    @Mock
    private PublicHolidayService publicHolidayService;
    @Mock
    private UserLeaveEntitlementService userLeaveEntitlementService;

    @InjectMocks
    private LeaveApplicationServiceImpl leaveApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Additional setup if required
    }

    @Test
    void testApproveApplicationNotFound() {
        // Arrange
        Long applicationId = 1L;
        when(leaveApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> leaveApplicationService.approveApplication(applicationId));
    }

    @Test
    void testApproveApplicationAlreadyApproved() {
        // Arrange
        Long applicationId = 1L;
        LeaveApplication existingApplication = new LeaveApplication();
        existingApplication.setStatus("Approved");
        when(leaveApplicationRepository.findById(applicationId)).thenReturn(Optional.of(existingApplication));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> leaveApplicationService.approveApplication(applicationId), "Application already approved");
    }

    @Test
    void testApproveApplicationAlreadyRejected() {
        // Arrange
        Long applicationId = 1L;
        LeaveApplication existingApplication = new LeaveApplication();
        existingApplication.setStatus("Rejected");
        when(leaveApplicationRepository.findById(applicationId)).thenReturn(Optional.of(existingApplication));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> leaveApplicationService.approveApplication(applicationId), "Application already rejected");
    }

    @Test
    void testApproveApplicationSuccess() {
        // Arrange
        Long applicationId = 1L;
        LeaveApplication application = new LeaveApplication();
        application.setId(applicationId);
        application.setStatus("Applied");
        application.setType("Annual");
        application.setStartDate(LocalDate.now());
        application.setEndDate(LocalDate.now().plusDays(2));

        Employee employee = new Employee();
        UserLeaveEntitlement entitlement = new UserLeaveEntitlement();
        entitlement.setAnnualEntitledDays(10); // Enough entitlement

        employee.setUserLeaveEntitlement(entitlement);
        application.setEmployee(employee);

        when(leaveApplicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(publicHolidayService.holidayWeekendDuration(any(LocalDate.class), any(LocalDate.class))).thenReturn(2L);
        when(userLeaveEntitlementService.save(any(UserLeaveEntitlement.class))).thenReturn(entitlement);
        when(leaveApplicationRepository.save(any(LeaveApplication.class))).thenReturn(application);

        // Act
        Boolean result = leaveApplicationService.approveApplication(applicationId);

        // Assert
        assertTrue(result);
        assertEquals("Approved", application.getStatus());
    }




}

package com.laps.backend;

import com.laps.backend.models.LeaveType;
import com.laps.backend.models.LeaveTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaveTypeTest{
    @Test
    public void testLeavetype(){
        String roleName = "Employee";
        LeaveTypeEnum leaveTypeEnum = LeaveTypeEnum.ANNUAL;
        int entiledNum = 18;

        LeaveType leavetype = new LeaveType(roleName,leaveTypeEnum,entiledNum);

        assertEquals(roleName, leavetype.getRoleName());
        assertEquals(leaveTypeEnum, leavetype.getName());
        assertEquals(entiledNum, leavetype.getEntitledNum());
    }

    @Test
    public void testManagerLeaveType(){
        LeaveType leaveType = new LeaveType();
        leaveType.setRoleName("Manager");
        leaveType.setName(LeaveTypeEnum.MEDICAL);
        leaveType.setEntitledNum(60);

        assertEquals("Manager", leaveType.getRoleName());
        assertEquals(LeaveTypeEnum.MEDICAL, leaveType.getName());
        assertEquals(60, leaveType.getEntitledNum());
    }

    @Test
    public void testAdminLeaveType(){
        LeaveType leaveType = new LeaveType();
        leaveType.setRoleName("Admin");
        leaveType.setName(LeaveTypeEnum.ANNUAL);
        leaveType.setEntitledNum(14);

        assertEquals("Admin", leaveType.getRoleName());
        assertEquals(LeaveTypeEnum.ANNUAL, leaveType.getName());
        assertEquals(14, leaveType.getEntitledNum());
    }

    @Test
    public void testLeaveTypeEdit(){
        LeaveType leaveType = new LeaveType("Employee",LeaveTypeEnum.ANNUAL,20);

        leaveType.setRoleName("Manager");
        leaveType.setName(LeaveTypeEnum.ANNUAL);
        leaveType.setEntitledNum(18);

        assertEquals("Manager",leaveType.getRoleName(),"Role updated");
        assertEquals(LeaveTypeEnum.ANNUAL,leaveType.getName(),"Leavetype updated");
        assertEquals(18,leaveType.getEntitledNum(),"Number of days updated");
    }
}

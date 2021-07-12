package com.tcra.hrms.services.leave;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.leave.EmployeeLeaveBalance;

@Component
public interface ILeaveBalanceServices {
	public ResponseEntity<EmployeeLeaveBalance[]> GetAll(String authToken);
	public ResponseEntity<EmployeeLeaveBalance[]> GetAllBySupervisor(String authToken,int supervisorId);
	public ResponseEntity<EmployeeLeaveBalance> GetAllByEmployee(String authToken,int employeeId);
}

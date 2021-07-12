package com.tcra.hrms.services.leave;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.leave.Leave;
import com.tcra.hrms.dto.leave.LeaveApprovalStatus;
@Component
public interface ILeaveServices {
	ResponseEntity<Leave[]> GetAll(String authToken);
	ResponseEntity<Leave[]> GetAllEmployee(String authToken,int employeeId);
	ResponseEntity<Leave[]> GetAllBySupervisor(String authToken,int supervisorId);
	ResponseEntity<Leave> Get(String authToken,int id);
	ResponseEntity<Leave> Create(String authToken,Leave leave);
	ResponseEntity<Leave> Update(String authToken,int id,Leave leave);
	ResponseEntity<Void> Delete(String authToken,int id);
	ResponseEntity<Void> Delete(String authToken,int id,int requesterid);
	
	ResponseEntity<Void> Approve(String authToken,int leaveid,int approverid,int status,String comment);	
	
	ResponseEntity<LeaveApprovalStatus[]> GetLeaveApprovers(String authToken,int id);
	
	ResponseEntity<Leave[]> GetAllLeaveApplicationsApprovedBySupervisorId(String authToken,int supervisorId, String startdate);
	
	ResponseEntity<Leave[]> GetAllLeaveApplicationsByEmployeeID(String authToken,int employeeId);
}

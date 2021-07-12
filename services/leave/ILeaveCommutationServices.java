package com.tcra.hrms.services.leave;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.leave.LeaveCommutation;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;

@Component
public interface ILeaveCommutationServices {
	public ResponseEntity<LeaveCommutation> Create(String authToken,LeaveCommutation commutation);
	public ResponseEntity<?> Create(String authToken,LeaveCommutation commutation,int requesterid);
	public ResponseEntity<LeaveCommutation[]> GetAll(String authToken);
	public ResponseEntity<LeaveCommutation[]> GetAllBySupervisor(String authToken,int supervisorId);
	public ResponseEntity<LeaveCommutation[]> GetAllByEmployee(String authToken,int employeeId);
	public ResponseEntity<LeaveCommutation> Update(String authToken,int id,LeaveCommutation commutation);
	public ResponseEntity<Double> GetAmount(String authToken,int employeeId,int days);
	
	public ResponseEntity<LeaveCommutation> Delete(String authToken,int id);
	
	public ResponseEntity<TrainingApprovalStatus[]> GetLeaveCommutationNotification(String authToken,int id);
	
	public ResponseEntity<LeaveCommutation[]> GetLeaveCommutationNotApprovedBySupervisorNext(String authToken,int supervisorId);
	
	public ResponseEntity<LeaveCommutation> AcknowledgeLeaveCommutation(String authToken,int id, int empid, int acknowledge);
	
	public ResponseEntity<Comment> ApproveLeaveCommutationV2(String authToken,int leavesoldid , int supervisorid , int status, Comment comment );
}

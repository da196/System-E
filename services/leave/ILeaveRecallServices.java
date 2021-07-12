package com.tcra.hrms.services.leave;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.leave.LeaveCommutation;
import com.tcra.hrms.dto.leave.Recall;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;

@Component
public interface ILeaveRecallServices {
	public ResponseEntity<Recall> Create(String authToken,Recall recall);
	public ResponseEntity<Recall> Create(String authToken,Recall recall,int requestedBy);
	public ResponseEntity<Recall> Get(String authToken,int id);
	public ResponseEntity<Recall[]> GetAll(String authToken);
	public ResponseEntity<Recall[]> GetAllBySupervisor(String authToken,int supervisorId);
	public ResponseEntity<Recall> Update(String authToken,int id,Recall recall);
	public ResponseEntity<Integer> Delete(String authToken,int id);
	
	
	public ResponseEntity<TrainingApprovalStatus[]> GetLeaveRecallNotification(String authToken,int id);
	
	public ResponseEntity<Recall[]> GetAllBySupervisorNext(String authToken,int supervisorId);
	
	public ResponseEntity<Recall[]> GetByEmployeeID(String authToken,int supervisorId);
	
	public ResponseEntity<Recall> AcknowledgeLeaveRecall(String authToken,int id, int empid, int acknowledge);
	
	ResponseEntity<Comment> ApproveLeaveRecall(String authToken, Comment commentEntity, int leaveid, int status, int supervisorid);
	
}

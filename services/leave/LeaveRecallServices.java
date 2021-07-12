package com.tcra.hrms.services.leave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.leave.LeaveCommutation;
import com.tcra.hrms.dto.leave.Recall;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;

@Component
public class LeaveRecallServices implements ILeaveRecallServices {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<Recall> Create(String authToken, Recall recall) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(recall, headers);
		final ResponseEntity<Recall> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/requestLeaveRecall/", HttpMethod.POST, request,
				Recall.class);
		return response;
	}

	@Override
	public ResponseEntity<Recall> Create(String authToken, Recall recall, int requestedBy) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(recall, headers);
		final ResponseEntity<Recall> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/requestLeaveRecall/" + requestedBy, HttpMethod.POST, request,
				Recall.class);
		return response;
	}

	@Override
	public ResponseEntity<Recall> Get(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(headers);
		final ResponseEntity<Recall> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/getLeaveRecallById/" + id, HttpMethod.GET, request,
				Recall.class);
		return response;
	}

	@Override
	public ResponseEntity<Recall[]> GetAll(String authToken) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(headers);
		final ResponseEntity<Recall[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/getAllLeaveRecall", HttpMethod.GET, request, Recall[].class);
		return response;
	}

	@Override
	public ResponseEntity<Recall[]> GetAllBySupervisor(String authToken, int supervisorId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(headers);
		final ResponseEntity<Recall[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/getAllLeaveRecallBySupervisorId/" + supervisorId, HttpMethod.GET,
				request, Recall[].class);
		return response;
	}

	@Override
	public ResponseEntity<Recall> Update(String authToken, int id, Recall recall) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(recall, headers);
		final ResponseEntity<Recall> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/requestLeaveRecall/" + id, HttpMethod.PUT, request,
				Recall.class);
		return response;
	}

	@Override
	public ResponseEntity<Integer> Delete(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<?> request = new HttpEntity<>(headers);
		final ResponseEntity<Integer> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/deleteLeaveRecall/" + id, HttpMethod.DELETE,
				request, Integer.class);
		return response;
	}
	
	
	@Override
	public ResponseEntity<TrainingApprovalStatus[]> GetLeaveRecallNotification(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<TrainingApprovalStatus> request = new HttpEntity<TrainingApprovalStatus>(headers);
		final ResponseEntity<TrainingApprovalStatus[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/getLeaveRecallApprovers/"+id, HttpMethod.GET, request,
				TrainingApprovalStatus[].class);
		return response;
	}
	
	@Override
	public ResponseEntity<Recall[]> GetAllBySupervisorNext(String authToken, int supervisorId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(headers);
		final ResponseEntity<Recall[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/getLeaveRecallNotApprovedBySupervisorNext/" + supervisorId, HttpMethod.GET,
				request, Recall[].class);
		return response;
	}
	
	@Override
	public ResponseEntity<Recall[]> GetByEmployeeID(String authToken, int supervisorId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(headers);
		final ResponseEntity<Recall[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/getAllLeaveRecallByEmployeeId/" + supervisorId, HttpMethod.GET,
				request, Recall[].class);
		return response;
	}
	
	@Override
	public ResponseEntity<Recall> AcknowledgeLeaveRecall(String authToken,int id, int empid, int acknowledge ) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Recall> request = new HttpEntity<Recall>(headers);
		final ResponseEntity<Recall> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveRecall/acknowledgeLeaveRecall/" + id + "/" + empid + "/" + acknowledge ,
				HttpMethod.GET, request,
				Recall.class);
		return response;
	}
	
	@Override
	public ResponseEntity<Comment> ApproveLeaveRecall(String authToken, Comment commentEntity, int leaveid, int status, int supervisorid) {
		
		if(!StringUtils.isEmpty(authToken)) {
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			final HttpEntity<Comment> request = new HttpEntity<Comment> (commentEntity,headers);		
			final ResponseEntity<Comment> response = restTemplate.exchange(AppConstants.BASE_URL+
					"/leaveRecall/ApproveLeaveRecall/" + leaveid + "/" + supervisorid + "/" +  status , 
					  HttpMethod.PUT, 
					  request, 
					  Comment.class);			
			return response;
		}
		return null;
	}
	
}

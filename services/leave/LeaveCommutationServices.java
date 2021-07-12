package com.tcra.hrms.services.leave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.leave.LeaveCommutation;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;

@Component
public class LeaveCommutationServices implements ILeaveCommutationServices {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<LeaveCommutation> Create(String authToken, LeaveCommutation commutation) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(commutation, headers);
		final ResponseEntity<LeaveCommutation> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/sellLeave", HttpMethod.POST, request, LeaveCommutation.class);
		return response;
	}

	@Override
	public ResponseEntity<LeaveCommutation[]> GetAll(String authToken) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(headers);
		final ResponseEntity<LeaveCommutation[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/getAllLeaveSell/", HttpMethod.GET, request,
				LeaveCommutation[].class);
		return response;
	}

	@Override
	public ResponseEntity<LeaveCommutation[]> GetAllBySupervisor(String authToken, int supervisorId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(headers);
		final ResponseEntity<LeaveCommutation[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/getAllLeaveCommutedNotApprovedYet/" + supervisorId, HttpMethod.GET, request,
				LeaveCommutation[].class);
		return response;
	}

	@Override
	public ResponseEntity<LeaveCommutation[]> GetAllByEmployee(String authToken, int employeeId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(headers);
		final ResponseEntity<LeaveCommutation[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/getLeaveSellByEmpId/" + employeeId, HttpMethod.GET, request,
				LeaveCommutation[].class);
		return response;
	}

	@Override
	public ResponseEntity<LeaveCommutation> Update(String authToken, int id, LeaveCommutation commutation) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(commutation, headers);
		final ResponseEntity<LeaveCommutation> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/updateLeaveSell/" + id, HttpMethod.PUT, request,
				LeaveCommutation.class);
		return response;
	}

//	@Override
//	public ResponseEntity<LeaveCommutation> Create(String authToken, LeaveCommutation commutation, int requesterid) {
//		// headers
//		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
//		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(commutation, headers);
//		final ResponseEntity<LeaveCommutation> response = restTemplate.exchange(
//				AppConstants.BASE_URL + "/leaveSell/sellLeave/" + requesterid, HttpMethod.POST, request,
//				LeaveCommutation.class);
//		return response;
//	}
	
	@Override
	public ResponseEntity<?> Create(String authToken, LeaveCommutation commutation, int requesterid) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<?> request = new HttpEntity<>(commutation, headers);
		final ResponseEntity<?> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/sellLeave/" + requesterid, HttpMethod.POST, request,
				LeaveCommutation.class);
		return response;
	}


	@Override
	public ResponseEntity<Double> GetAmount(String authToken, int employeeId, int days) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<?> request = new HttpEntity<>(headers);
		final ResponseEntity<Double> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/getCommutableAmountV2/" + employeeId + "/" + days, HttpMethod.GET,
				request, Double.class);
		return response;
	}

	/*
	 * @Override public ResponseEntity<Object> GetApproval(String authToken, int
	 * commutationid) { // headers final HttpHeaders headers =
	 * HttpHeader.setHttpHeader(authToken); final HttpEntity<?> request = new
	 * HttpEntity<>(headers); final ResponseEntity<Object> response =
	 * restTemplate.exchange( AppConstants.BASE_URL +
	 * "/leaveApplications/getLeaveApprovers/" + commutationid, HttpMethod.GET,
	 * request, Object.class); return response; }
	 */
	@Override
	public ResponseEntity<LeaveCommutation> Delete(String authToken,int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(headers);
		final ResponseEntity<LeaveCommutation> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/deleteLeaveSell/" + id, HttpMethod.DELETE, request,
				LeaveCommutation.class);
		return response;
	}
	
	@Override
	public ResponseEntity<TrainingApprovalStatus[]> GetLeaveCommutationNotification(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<TrainingApprovalStatus> request = new HttpEntity<TrainingApprovalStatus>(headers);
		final ResponseEntity<TrainingApprovalStatus[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/getLeaveCommutationApprovers/"+id, HttpMethod.GET, request,
				TrainingApprovalStatus[].class);
		return response;
	}
	
	@Override
	public ResponseEntity<LeaveCommutation[]> GetLeaveCommutationNotApprovedBySupervisorNext(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(headers);
		final ResponseEntity<LeaveCommutation[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/getLeaveCommutationNotApprovedBySupervisorNext/"+id, HttpMethod.GET, request,
				LeaveCommutation[].class);
		return response;
	}
	
	@Override
	public ResponseEntity<LeaveCommutation> AcknowledgeLeaveCommutation(String authToken,int id, int empid, int acknowledge ) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveCommutation> request = new HttpEntity<LeaveCommutation>(headers);
		final ResponseEntity<LeaveCommutation> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/acknowledgeLeaveCommutation/" + id + "/" + empid + "/" + acknowledge ,
				HttpMethod.PUT, request,
				LeaveCommutation.class);
		return response;
	}
	
	
	@Override
	public ResponseEntity<Comment> ApproveLeaveCommutationV2(String authToken,int leavesoldid , int supervisorid , int status, Comment comment ) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Comment> request = new HttpEntity<Comment>(comment, headers);
		final ResponseEntity<Comment> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveSell/ApproveLeaveCommutationV2/" + leavesoldid + "/" + supervisorid + "/" + status ,
				HttpMethod.PUT, request,
				Comment.class);
		return response;
	}
	
}

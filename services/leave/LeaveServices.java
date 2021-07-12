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
import com.tcra.hrms.dto.leave.Leave;
import com.tcra.hrms.dto.leave.LeaveApprovalStatus;

@Component
public class LeaveServices implements ILeaveServices {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<Leave[]> GetAll(String authToken) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Leave[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getAllLeaveApplications", HttpMethod.GET, request,
				Leave[].class);
		return response;
	}

	@Override
	public ResponseEntity<Leave[]> GetAllEmployee(String authToken, int employeeId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Leave[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getAllLeaveApplicationsByEmpId/" + employeeId,
				HttpMethod.GET, request, Leave[].class);
		return response;
	}

	@Override
	public ResponseEntity<Leave[]> GetAllBySupervisor(String authToken, int supervisorId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Leave[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getLeaveNotApprovedBySupervisorNext/" + supervisorId,
				HttpMethod.GET, request, Leave[].class);
		return response;
	}

	@Override
	public ResponseEntity<Leave> Get(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Leave> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getLeaveApplicationByLeaveId/" + id, HttpMethod.GET,
				request, Leave.class);
		return response;
	}

	@Override
	public ResponseEntity<Leave> Create(String authToken, Leave leave) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(leave, headers);
		final ResponseEntity<Leave> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/applyLeave", HttpMethod.POST, request, Leave.class);
		return response;
	}

	@Override
	public ResponseEntity<Leave> Update(String authToken, int id, Leave leave) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(leave, headers);
		final ResponseEntity<Leave> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/updateLeave/" + id, 
				HttpMethod.PUT, request, Leave.class);
		return response;
	}

	@Override
	public ResponseEntity<Void> Delete(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Void> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/deleteLeave/" + id,
				HttpMethod.DELETE, request, Void.class);
		return response;
	}

	@Override
	public ResponseEntity<Void> Approve(String authToken, int leaveid, int approverid, int status, String comment) {
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<?> request = new HttpEntity<>(headers);
		final ResponseEntity<Void> response = restTemplate.exchange(AppConstants.BASE_URL
				+ "/leaveApplications/approveLeaveV2/" + leaveid + "/" + approverid + "/" + status + "/" + comment,
				HttpMethod.PUT, request, Void.class);
		return response;
	}

	@Override
	public ResponseEntity<Void> Delete(String authToken, int id, int requesterid) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<?> request = new HttpEntity<>(headers);
		final ResponseEntity<Void> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/deleteLeave/" + id + "/" + requesterid, HttpMethod.DELETE,
				request, Void.class);
		return response;
	}

	/*
	 * @Override public ResponseEntity<Object> GetLeaveApproval(String authToken,int
	 * leaveid) { // headers final HttpHeaders headers =
	 * HttpHeader.setHttpHeader(authToken); final HttpEntity<Leave> request = new
	 * HttpEntity<Leave>(headers); final ResponseEntity<Object> response =
	 * restTemplate.exchange( AppConstants.BASE_URL +
	 * "/leaveApplications/getLeaveApprovers/" + leaveid, HttpMethod.GET, request,
	 * Object.class); return response; }
	 */

	@Override
	public ResponseEntity<LeaveApprovalStatus[]> GetLeaveApprovers(String authToken, int id) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveApprovalStatus> request = new HttpEntity<LeaveApprovalStatus>(headers);
		final ResponseEntity<LeaveApprovalStatus[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getLeaveApprovers/" + id,
				HttpMethod.GET, request, LeaveApprovalStatus[].class);
		return response;
	}

	public ResponseEntity<Leave[]> GetAllLeaveApplicationsApprovedBySupervisorId(String authToken,int supervisorId, String startdate) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Leave[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getAllLeaveApplicationsApprovedBySupervisorId/" + supervisorId + "/" + startdate,
				HttpMethod.GET, request, Leave[].class);
		return response;
	}

	@Override
	public ResponseEntity<Leave[]> GetAllLeaveApplicationsByEmployeeID(String authToken,int employeeId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Leave> request = new HttpEntity<Leave>(headers);
		final ResponseEntity<Leave[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveApplications/getAllLeaveApplicationsByEmpId/" + employeeId,
				HttpMethod.GET, request, Leave[].class);
		return response;
	}
}

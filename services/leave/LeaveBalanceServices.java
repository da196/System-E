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
import com.tcra.hrms.dto.leave.EmployeeLeaveBalance;

@Component
public class LeaveBalanceServices implements ILeaveBalanceServices {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<EmployeeLeaveBalance[]> GetAll(String authToken) {
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<EmployeeLeaveBalance> request = new HttpEntity<EmployeeLeaveBalance>(headers);
		final ResponseEntity<EmployeeLeaveBalance[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/HrmsLeaveBalance/getAllLeaveBalance", HttpMethod.GET, request,
				EmployeeLeaveBalance[].class);
		return response;
	}

	@Override
	public ResponseEntity<EmployeeLeaveBalance> GetAllByEmployee(String authToken, int employeeId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<EmployeeLeaveBalance> request = new HttpEntity<EmployeeLeaveBalance>(headers);
		final ResponseEntity<EmployeeLeaveBalance> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/HrmsLeaveBalance/getLeaveBalanceByEmpId/"+employeeId, HttpMethod.GET, request,
				EmployeeLeaveBalance.class);
		return response;
	}

	@Override
	public ResponseEntity<EmployeeLeaveBalance[]> GetAllBySupervisor(String authToken, int supervisorId) {
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<EmployeeLeaveBalance> request = new HttpEntity<EmployeeLeaveBalance>(headers);
		final ResponseEntity<EmployeeLeaveBalance[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/HrmsLeaveBalance/getLeaveBalanceBySupervisorId/"+supervisorId, HttpMethod.GET, request,
				EmployeeLeaveBalance[].class);
		return response;
	}

}

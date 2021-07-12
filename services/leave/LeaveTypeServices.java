package com.tcra.hrms.services.leave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.leave.LeaveType;

@Service
public class LeaveTypeServices implements ILeaveTypeServices {
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	private String authToken;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<LeaveType[]> GetAll() {
		// token
		authToken = authenticationFacade.getAuthenticationToken();
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<LeaveType> request = new HttpEntity<LeaveType>(headers);
		final ResponseEntity<LeaveType[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/leaveType/getAllLeaveType", HttpMethod.GET, request, LeaveType[].class);
		return response;
	}

}

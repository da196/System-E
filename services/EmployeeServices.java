package com.tcra.hrms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.Employee;

@Component
public class EmployeeServices implements IEmployeeServices {
	// Access Token Configurations.
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	private String authToken;

	// constructor
	public EmployeeServices() {
	}

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<Employee[]> GetAll() {
		// token
		authToken = authenticationFacade.getAuthenticationToken();
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Employee> request = new HttpEntity<Employee>(headers);
		final ResponseEntity<Employee[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/hrmsEmployee/getAllEmployee", HttpMethod.GET, request, Employee[].class);
		return response;
	}

	@Override
	public ResponseEntity<Employee> Create(Employee employee) {
		if (employee != null) {
			// token
			authToken = authenticationFacade.getAuthenticationToken();
			// headers
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
			HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
			ResponseEntity<Employee> response = restTemplate.exchange(
					AppConstants.BASE_URL + "/hrmsEmployee/addEmployee", HttpMethod.POST, request, Employee.class);

			return response;
		}
		return null;
	}

	@Override
	public ResponseEntity<Employee[]> GetAllLess() {
		// token
		authToken = authenticationFacade.getAuthenticationToken();
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Employee> request = new HttpEntity<Employee>(headers);
		final ResponseEntity<Employee[]> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/hrmsEmployee/getallemployeelessdetails", HttpMethod.GET, request,
				Employee[].class);
		return response;
	}

	@Override
	public ResponseEntity<Employee> Get(int id) {
		// token
		authToken = authenticationFacade.getAuthenticationToken();
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Employee> request = new HttpEntity<Employee>(headers);
		final ResponseEntity<Employee> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/hrmsEmployee/getEmployee/"+id, HttpMethod.GET, request, Employee.class);
		return response;
	}

}

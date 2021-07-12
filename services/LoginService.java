package com.tcra.hrms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.entity.EmployeeEntity;
import com.tcra.hrms.entity.UserEntity;


@Component
public class LoginService implements ILoginServices{

	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate; //restTemplate
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public ResponseEntity<UserEntity> Login(UserEntity userEntity) {		
		try {
			
		if(userEntity != null) {
			String authToken = "";			
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<UserEntity> request = new HttpEntity<>(userEntity, headers);
			System.out.println(request);
			ResponseEntity<UserEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/authController/authenticate", 
					  HttpMethod.POST, 
					  request, 
					  UserEntity.class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeEntity> GetEmployeeGeneralInfo(String authToken, String email){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeEntity> request = new HttpEntity<EmployeeEntity>(headers);	  		
			ResponseEntity<EmployeeEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/hrmsEmployee/getEmployeeByEmail/"+ email, 
					  HttpMethod.GET, 
					  request, 
					  EmployeeEntity.class);
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
}

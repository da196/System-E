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
import com.tcra.hrms.entity.DashboardEmployeeStatistics;


@Component
public class DashboardService  implements IDashboardService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate; //restTemplate
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public ResponseEntity<DashboardEmployeeStatistics> GetDahboardOverall(int empID, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<DashboardEmployeeStatistics> request = new HttpEntity<DashboardEmployeeStatistics>(headers);	  		
			ResponseEntity<DashboardEmployeeStatistics> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeStatistics/getempdepartstatByempId/" + empID, 
					  HttpMethod.GET, 
					  request, 
					  DashboardEmployeeStatistics.class);
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}


}

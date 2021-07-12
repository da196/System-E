package com.tcra.hrms.services;

import org.apache.log4j.Logger;
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
import com.tcra.hrms.dto.EducationCourse;
import com.tcra.hrms.dto.ShortCourse;
import com.tcra.hrms.dto.ShortCourse;

@Component
public class ShortCourseService implements IShortCourseService {
	private static final Logger logger = Logger.getLogger(ShortCourseService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<ShortCourse[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<ShortCourse> request = new HttpEntity<ShortCourse>(headers);	
			final ResponseEntity<ShortCourse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/EmployeeShortCourses/listEmployeeShortCourses", 
					  HttpMethod.GET, 
					  request, 
					  ShortCourse[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<ShortCourse[]> GetAll(String authToken, int employeeId) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<ShortCourse> request = new HttpEntity<ShortCourse>(headers);	
			final ResponseEntity<ShortCourse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/EmployeeShortCourses/getEmployeeShortCoursesByEmpId/"+employeeId, 
					  HttpMethod.GET, 
					  request, 
					  ShortCourse[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<ShortCourse> Get(String authToken, int id) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<ShortCourse> request = new HttpEntity<ShortCourse>(headers);	
			final ResponseEntity<ShortCourse> response = restTemplate.exchange(AppConstants.BASE_URL+"/EmployeeShortCourses/getEmployeeShortCourseById/"+id, 
					  HttpMethod.GET, 
					  request, 
					  ShortCourse.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<ShortCourse> Add(String authToken, ShortCourse course) {
		if(StringUtils.isEmpty(authToken)) {
			logger.info("Access Token is empty");
			return null;
		}	
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
		final HttpEntity<ShortCourse> request = new HttpEntity<ShortCourse>(course,headers);	
		final ResponseEntity<ShortCourse> response = 
				restTemplate.exchange(AppConstants.BASE_URL+"/EmployeeShortCourses/addEmployeeShortCourse", 
				  HttpMethod.POST, 
				  request, 
				  ShortCourse.class);				
		return response;
	}

	@Override
	public ResponseEntity<ShortCourse> Update(String authToken, int id, ShortCourse course) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<ShortCourse> request = new HttpEntity<ShortCourse>(course,headers);	
			final ResponseEntity<ShortCourse> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/EmployeeShortCourses/updateEmployeeShortCourse/"+id, 
					  HttpMethod.PUT, 
					  request, 
					  ShortCourse.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - "+ex.toString());
			logger.info("Exception message - "+ex.getMessage());
			logger.info("Exception message localized - "+ex.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public ResponseEntity<Integer> Delete(String authToken, int id) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<Integer> request = new HttpEntity<Integer>(headers);	
			final ResponseEntity<Integer> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/EmployeeShortCourses/deleteEmployeeShortCourse/"+id, 
					  HttpMethod.DELETE, 
					  request, 
					  Integer.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}
}

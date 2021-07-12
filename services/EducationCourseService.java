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

@Component
public class EducationCourseService implements IEducationCourseService {
	private static final Logger logger = Logger.getLogger(EducationCourseService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<EducationCourse[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationCourse> request = new HttpEntity<EducationCourse>(headers);	
			final ResponseEntity<EducationCourse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/getAllEducationcourse", 
					  HttpMethod.GET, 
					  request, 
					  EducationCourse[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationCourse[]> GetAll(String authToken, int levelId) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationCourse> request = new HttpEntity<EducationCourse>(headers);	
			final ResponseEntity<EducationCourse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/getEducationcourseByLevelCode/"+levelId, 
					  HttpMethod.GET, 
					  request, 
					  EducationCourse[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationCourse> Get(String authToken, int id) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationCourse> request = new HttpEntity<EducationCourse>(headers);	
			final ResponseEntity<EducationCourse> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/getEducationcourse/"+id, 
					  HttpMethod.GET, 
					  request, 
					  EducationCourse.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationCourse> Add(String authToken, EducationCourse course) {
		if(StringUtils.isEmpty(authToken)) {
			logger.info("Access Token is empty");
			return null;
		}	
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
		final HttpEntity<EducationCourse> request = new HttpEntity<EducationCourse>(course,headers);	
		final ResponseEntity<EducationCourse> response = 
				restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/addEducationcourse", 
				  HttpMethod.POST, 
				  request, 
				  EducationCourse.class);				
		return response;
	}

	@Override
	public ResponseEntity<EducationCourse> Update(String authToken, int id, EducationCourse course) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationCourse> request = new HttpEntity<EducationCourse>(course,headers);	
			final ResponseEntity<EducationCourse> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/updateEducationcourse/"+id, 
					  HttpMethod.PUT, 
					  request, 
					  EducationCourse.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
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
					restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/deleteEducationcourse/"+id, 
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

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
import com.tcra.hrms.dto.EducationInstitution;

@Component
public class EducationInstitutionService implements IEducationInstitutionService {
	private static final Logger logger = Logger.getLogger(EducationInstitutionService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<EducationInstitution[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitution> request = new HttpEntity<EducationInstitution>(headers);	
			final ResponseEntity<EducationInstitution[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/getAllEducationinstitution", 
					  HttpMethod.GET, 
					  request, 
					  EducationInstitution[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationInstitution[]> GetAll(String authToken, int countryId) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitution> request = new HttpEntity<EducationInstitution>(headers);	
			final ResponseEntity<EducationInstitution[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/getAllEducationinstitutionByCountryId/"+countryId, 
					  HttpMethod.GET, 
					  request, 
					  EducationInstitution[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationInstitution> Get(String authToken, int id) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitution> request = new HttpEntity<EducationInstitution>(headers);	
			final ResponseEntity<EducationInstitution> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/getEducationinstitutionDetailed/"+id, 
					  HttpMethod.GET, 
					  request, 
					  EducationInstitution.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationInstitution> Add(String authToken, EducationInstitution institution) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitution> request = new HttpEntity<EducationInstitution>(institution,headers);	
			final ResponseEntity<EducationInstitution> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/addEducationinstitution", 
					  HttpMethod.POST, 
					  request, 
					  EducationInstitution.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<EducationInstitution> Update(String authToken, int id, EducationInstitution institution) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitution> request = new HttpEntity<EducationInstitution>(institution,headers);	
			final ResponseEntity<EducationInstitution> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/updateEducationinstitution/"+id, 
					  HttpMethod.PUT, 
					  request, 
					  EducationInstitution.class);				
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
					restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/deleteEducationinstitution/"+id, 
					  HttpMethod.GET, 
					  request, 
					  Integer.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

}

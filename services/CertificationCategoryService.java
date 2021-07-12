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
import com.tcra.hrms.dto.CertificationCategory;
import com.tcra.hrms.dto.EducationCourse;
import com.tcra.hrms.dto.CertificationCategory;
import com.tcra.hrms.dto.CertificationCategory;

@Component
public class CertificationCategoryService implements ICertificationCategoryService{
	private static final Logger logger = Logger.getLogger(CertificationCategoryService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<CertificationCategory[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<CertificationCategory> request = new HttpEntity<CertificationCategory>(headers);	
			final ResponseEntity<CertificationCategory[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/certificationCategory/getAllCertificationCategory", 
					  HttpMethod.GET, 
					  request, 
					  CertificationCategory[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<CertificationCategory> Get(String authToken, int id) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<CertificationCategory> request = new HttpEntity<CertificationCategory>(headers);	
			final ResponseEntity<CertificationCategory> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/certificationCategory/getAllCertificationCategoryById/"+id, 
					  HttpMethod.GET, 
					  request, 
					  CertificationCategory.class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<CertificationCategory> Add(String authToken, CertificationCategory category) {
		if(StringUtils.isEmpty(authToken)) {
			logger.info("Access Token is empty");
			return null;
		}	
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
		final HttpEntity<CertificationCategory> request = new HttpEntity<CertificationCategory>(category,headers);	
		final ResponseEntity<CertificationCategory> response = 
				restTemplate.exchange(AppConstants.BASE_URL+"/certificationCategory/addCertificationCategory", 
				  HttpMethod.POST, 
				  request, 
				  CertificationCategory.class);				
		return response;
	}

	@Override
	public ResponseEntity<CertificationCategory> Update(String authToken, int id, CertificationCategory category) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<CertificationCategory> request = new HttpEntity<CertificationCategory>(category,headers);	
			final ResponseEntity<CertificationCategory> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/certificationCategory/updateCertificationCategory/"+id, 
					  HttpMethod.PUT, 
					  request, 
					  CertificationCategory.class);				
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
					restTemplate.exchange(AppConstants.BASE_URL+"/certificationCategory/deleteCertificationCategory/"+id, 
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

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
import com.tcra.hrms.dto.EducationInstitutionCategory;

@Component
public class EducationInstitutionCategoryService implements IEducationInstitutionCategoryService{

	private static final Logger logger = Logger.getLogger(EducationInstitutionCategoryService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<EducationInstitutionCategory[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitutionCategory> request = new HttpEntity<EducationInstitutionCategory>(headers);	
			final ResponseEntity<EducationInstitutionCategory[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationInstCategory/getAllEducationInstCategory", 
					  HttpMethod.GET, 
					  request, 
					  EducationInstitutionCategory[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

}

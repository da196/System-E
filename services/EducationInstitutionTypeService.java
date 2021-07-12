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
import com.tcra.hrms.dto.EducationInstitutionType;

@Component
public class EducationInstitutionTypeService implements IEducationInstitutionTypeService{

	private static final Logger logger = Logger.getLogger(EducationInstitutionTypeService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<EducationInstitutionType[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<EducationInstitutionType> request = new HttpEntity<EducationInstitutionType>(headers);	
			final ResponseEntity<EducationInstitutionType[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationInstitutionType/getAllEducationInstitutionType", 
					  HttpMethod.GET, 
					  request, 
					  EducationInstitutionType[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

}

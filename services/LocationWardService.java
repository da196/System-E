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
import com.tcra.hrms.dto.Ward;
import com.tcra.hrms.dto.Ward;

@Component
public class LocationWardService implements ILocationWardService {
	
	private static final Logger logger = Logger.getLogger(LocationDistrictService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<Ward[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<Ward> request = new HttpEntity<Ward>(headers);	
			final ResponseEntity<Ward[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationWard/getAllLocationWard", 
					  HttpMethod.GET, 
					  request, 
					  Ward[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<Ward[]> GetAll(String authToken, int districtId) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<Ward> request = new HttpEntity<Ward>(headers);	
			final ResponseEntity<Ward[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationWard/getAllLocationWardByDistrictId/"+districtId, 
					  HttpMethod.GET, 
					  request, 
					  Ward[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<Ward> Get(String authToken, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Ward> Add(String authToken, Ward ward) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Ward> Update(String authToken, int id, Ward ward) {
		// TODO Auto-generated method stub
		return null;
	}

}

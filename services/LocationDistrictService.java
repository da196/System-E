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
import com.tcra.hrms.dto.City;
import com.tcra.hrms.dto.District;

@Component
public class LocationDistrictService implements ILocationDistrictService {

	private static final Logger logger = Logger.getLogger(LocationDistrictService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<District[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<District> request = new HttpEntity<District>(headers);	
			final ResponseEntity<District[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/LocationDistrict/getAllLocationDistrict", 
					  HttpMethod.GET, 
					  request, 
					  District[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<District[]> GetAll(String authToken, int cityId) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<District> request = new HttpEntity<District>(headers);	
			final ResponseEntity<District[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/LocationDistrict/getAllLocationDistrictByCityId/"+cityId, 
					  HttpMethod.GET, 
					  request, 
					  District[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<District> Get(String authToken, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<District> Add(String authToken, District district) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<District> Update(String authToken, int id, District district) {
		// TODO Auto-generated method stub
		return null;
	}

}

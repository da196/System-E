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

@Component
public class LocationCityService implements ILocationCityService {
	
	private static final Logger logger = Logger.getLogger(LocationCityService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
		
	@Override
	public ResponseEntity<City[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<City> request = new HttpEntity<City>(headers);	
			final ResponseEntity<City[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationCity/getAllLocationCity", 
					  HttpMethod.GET, 
					  request, 
					  City[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<City[]> GetAll(String authToken, int countryId) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<City> request = new HttpEntity<City>(headers);	
			final ResponseEntity<City[]> response = 
					restTemplate.exchange(AppConstants.BASE_URL+"/locationCity/getAllLocationCityByCountryId/"+countryId, 
					  HttpMethod.GET, 
					  request, 
					  City[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<City> Get(String authToken, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<City> Add(String authToken, City city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<City> Update(String authToken, int id, City city) {
		// TODO Auto-generated method stub
		return null;
	}

}

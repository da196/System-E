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
import com.tcra.hrms.dto.Country;

@Component
public class LocationCountryService implements ILocationCountryService {
	
	private static final Logger logger = Logger.getLogger(LocationCountryService.class); // log4j

	@Autowired
	private RestTemplate restTemplate;
		
	@Override
	public ResponseEntity<Country[]> GetAll(String authToken) {
		try {
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}	
			final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			final HttpEntity<Country> request = new HttpEntity<Country>(headers);	
			final ResponseEntity<Country[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationCountry/getAllLocationCountry", 
					  HttpMethod.GET, 
					  request, 
					  Country[].class);				
			return response;
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;
		}
	}

	@Override
	public ResponseEntity<Country> Get(String authToken,int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Country> Add(String authToken,Country country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Country> Update(String authToken,int id, Country country) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.tcra.hrms.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.dto.City;
import com.tcra.hrms.services.ILocationCityService;

@Controller
public class LocationCityController {
	private static final Logger logger = Logger.getLogger(LocationCityController.class); // log4j
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;	
	@Autowired
	private ILocationCityService locationCityService;
	
	@RequestMapping(value = "/city.htm/all", method = RequestMethod.GET)
	public @ResponseBody List<City> getCityAll(){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}
			final ResponseEntity<City[]> response = locationCityService.GetAll(authToken);
			if(response==null) {
				return null;
			}
			int statusCode = response.getStatusCodeValue();
			if(statusCode==HttpStatus.OK.value()) {
				return Arrays.asList(response.getBody());
			}
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;	
		}	
		return null;
	}
	
	@RequestMapping(value = "/city-in-country.htm/{countryId}", method = RequestMethod.GET)
	public @ResponseBody List<City> getCityAll(@PathVariable("countryId")int countryId){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}
			if(countryId<=0) {
				logger.info(String.format("Country ID = %d not valid", countryId));
				return null;
			}
			final ResponseEntity<City[]> response = locationCityService.GetAll(authToken,countryId);
			if(response==null) {
				return null;
			}
			int statusCode = response.getStatusCodeValue();
			if(statusCode==HttpStatus.OK.value()) {
				return Arrays.asList(response.getBody());
			}
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;	
		}	
		return null;
	}
}

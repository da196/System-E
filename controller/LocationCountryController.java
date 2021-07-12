package com.tcra.hrms.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.dto.Country;
import com.tcra.hrms.services.ILocationCountryService;

@Controller
public class LocationCountryController {
	
	private static final Logger logger = Logger.getLogger(LocationCountryController.class); // log4j
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;	
	@Autowired
	private ILocationCountryService locationCountryService;
	
	@RequestMapping(value = "/country.htm/all", method = RequestMethod.GET)
	public @ResponseBody List<Country> getCountryAll(){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}
			final ResponseEntity<Country[]> countries = locationCountryService.GetAll(authToken);
			if(countries==null) {
				return null;
			}
			int statusCode = countries.getStatusCodeValue();
			if(statusCode==HttpStatus.OK.value()) {
				return Arrays.asList(countries.getBody());
			}
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			return null;	
		}	
		return null;
	}
}

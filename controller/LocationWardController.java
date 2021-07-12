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
import com.tcra.hrms.dto.Ward;
import com.tcra.hrms.services.ILocationDistrictService;
import com.tcra.hrms.services.ILocationWardService;

@Controller
public class LocationWardController {
	private static final Logger logger = Logger.getLogger(LocationDistrictController.class); // log4j
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;	
	@Autowired
	private ILocationWardService locationWardService;
	
	@RequestMapping(value = "/ward.htm/all", method = RequestMethod.GET)
	public @ResponseBody List<Ward> getWardAll(){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}
			final ResponseEntity<Ward[]> response = locationWardService.GetAll(authToken);
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
	
	@RequestMapping(value = "/ward-in-district.htm/{districtId}", method = RequestMethod.GET)
	public @ResponseBody List<Ward> getWardAll(@PathVariable("districtId")int districtId){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				return null;
			}
			if(districtId<=0) {
				logger.info(String.format("District ID = %d not valid", districtId));
				return null;
			}
			final ResponseEntity<Ward[]> response = locationWardService.GetAll(authToken,districtId);
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

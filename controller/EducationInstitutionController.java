package com.tcra.hrms.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.dto.EducationCourse;
import com.tcra.hrms.dto.EducationInstitution;
import com.tcra.hrms.services.IEducationInstitutionService;

@Controller
public class EducationInstitutionController {
	private static final Logger logger = Logger.getLogger(EducationInstitutionController.class); // log4j
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;	
	@Autowired
	private IEducationInstitutionService institutionService;
	
	@RequestMapping(value = "/education-institution-add.htm", method = RequestMethod.POST,
			produces ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public @ResponseBody ResponseEntity<EducationInstitution> Add(Model model, @ModelAttribute("educationInstitution") @Valid EducationInstitution institution,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		final String authToken = authenticationFacade.getAuthenticationToken();
		// log request
		System.out.println("Institution=["+institution.toString()+"]");
		logger.info("Institution=["+institution.toString()+"]");
		
		final ResponseEntity<EducationInstitution> response = institutionService.Add(authToken, institution);
		if(response!=null && response.getBody()!=null) {
			// log result
			logger.info("Response=["+response.getBody().toString()+"]");
			return response;
		}
		logger.info("Response is NULL");
		return null; 		
	}
	
	@RequestMapping(value = "/education-institution-all.htm", method = RequestMethod.GET)
	public @ResponseBody List<EducationInstitution> GetAll(
			HttpServletRequest request
			,RedirectAttributes redirectAttributes){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				redirectAttributes.addFlashAttribute("error","Access token not valid"); 
				return null;
			}
			final ResponseEntity<EducationInstitution[]> response = institutionService.GetAll(authToken);
			if(response==null) {
				redirectAttributes.addFlashAttribute("error","Institution not found");
				return null;
			}
			int statusCode = response.getStatusCodeValue();
			if(statusCode==HttpStatus.OK.value()) {
				return Arrays.asList(response.getBody());
			}
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			redirectAttributes.addFlashAttribute("error","System exception");
			return null;	
		}	
		return null;
	}	
	
	@RequestMapping(value = "/education-institution-by-country-code.htm/{countryId}", method = RequestMethod.GET)
	public @ResponseBody List<EducationInstitution> GetAll(
			@PathVariable("countryId") int countryId
			,HttpServletRequest request
			,RedirectAttributes redirectAttributes){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				redirectAttributes.addFlashAttribute("error","Access token not valid"); 
				return null;
			}
			final ResponseEntity<EducationInstitution[]> response = institutionService.GetAll(authToken, countryId);
			if(response==null) {
				redirectAttributes.addFlashAttribute("error","Institution not found");
				return null;
			}
			int statusCode = response.getStatusCodeValue();
			if(statusCode==HttpStatus.OK.value()) {
				return Arrays.asList(response.getBody());
			}
		}catch(Exception ex) {
			logger.info("Exception - [Message = "+ex.getMessage()+",Details = "+ex.toString()+"]");
			redirectAttributes.addFlashAttribute("error","System exception");
			return null;	
		}	
		return null;
	}	

}

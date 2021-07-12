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
import com.tcra.hrms.services.IEducationCourseService;

@Controller
public class EducationCourseController {
	private static final Logger logger = Logger.getLogger(EducationCourseController.class); // log4j
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;	
	@Autowired
	private IEducationCourseService courseService;
	
	@RequestMapping(value = "/education-course-add.htm", method = RequestMethod.POST,
			produces ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public @ResponseBody ResponseEntity<EducationCourse> Add(Model model, @ModelAttribute("educationCourse") @Valid EducationCourse course,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		final String authToken = authenticationFacade.getAuthenticationToken();
		// log request
		System.out.println("Course=["+course.toString()+"]");
		logger.info("Course=["+course.toString()+"]");
		
		final ResponseEntity<EducationCourse> response = courseService.Add(authToken, course);
		if(response!=null && response.getBody()!=null) {
			// log result
			logger.info("Response=["+response.getBody().toString()+"]");
			return response;
		}
		logger.info("Response is NULL");
		return null; 		
	}
	
	@RequestMapping(value = "/education-course-all.htm", method = RequestMethod.GET)
	public @ResponseBody List<EducationCourse> GetAll(
			HttpServletRequest request
			,RedirectAttributes redirectAttributes){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				redirectAttributes.addFlashAttribute("error","Access token not valid"); 
				return null;
			}
			final ResponseEntity<EducationCourse[]> response = courseService.GetAll(authToken);
			if(response==null) {
				redirectAttributes.addFlashAttribute("error","Course not found");
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
	
	@RequestMapping(value = "/education-course-by-level-code.htm/{levelCode}", method = RequestMethod.GET)
	public @ResponseBody List<EducationCourse> GetAll(
			@PathVariable("levelCode") int levelCode
			,HttpServletRequest request
			,RedirectAttributes redirectAttributes){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				redirectAttributes.addFlashAttribute("error","Access token not valid"); 
				return null;
			}
			final ResponseEntity<EducationCourse[]> response = courseService.GetAll(authToken, levelCode);
			if(response==null) {
				redirectAttributes.addFlashAttribute("error","Course not found");
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

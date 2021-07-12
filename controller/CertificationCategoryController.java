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
import com.tcra.hrms.dto.CertificationCategory;
import com.tcra.hrms.dto.CertificationCategory;
import com.tcra.hrms.dto.CertificationCategory;
import com.tcra.hrms.services.ICertificationCategoryService;
import com.tcra.hrms.services.IEducationCourseService;

@Controller
public class CertificationCategoryController {
	private static final Logger logger = Logger.getLogger(CertificationCategoryController.class); // log4j
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;	
	@Autowired
	private ICertificationCategoryService certificationCategoryService;
	
	@RequestMapping(value = "/certification-category-add.htm", method = RequestMethod.POST,
	produces ={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public @ResponseBody ResponseEntity<CertificationCategory> Add(Model model, 
			@ModelAttribute("certificationCategory") @Valid CertificationCategory category,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		final String authToken = authenticationFacade.getAuthenticationToken();
		// log request
		System.out.println("Category=["+category.toString()+"]");
		logger.info("Category=["+category.toString()+"]");
		
		final ResponseEntity<CertificationCategory> response = certificationCategoryService.Add(authToken, category);
		if(response!=null && response.getBody()!=null) {
			int status = response.getStatusCodeValue();
			if(status==HttpStatus.OK.value()) {
				// ok -- 200
				return response;
			}else if(status==HttpStatus.ALREADY_REPORTED.value()) {
				// exists--208
				redirectAttributes.addFlashAttribute("error","Category already exists.");
			}
		}
		logger.info("Response is NULL");
		return null; 		
	}
	
	@RequestMapping(value = "/certification-category-all.htm", method = RequestMethod.GET)
	public @ResponseBody List<CertificationCategory> GetAll(
			HttpServletRequest request
			,RedirectAttributes redirectAttributes){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				redirectAttributes.addFlashAttribute("error","Access token not valid"); 
				return null;
			}
			final ResponseEntity<CertificationCategory[]> response = certificationCategoryService.GetAll(authToken);
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
	
	@RequestMapping(value = "/certification-category.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody List<CertificationCategory> GetAll(
			@PathVariable("id") int id
			,HttpServletRequest request
			,RedirectAttributes redirectAttributes){
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			if(StringUtils.isEmpty(authToken)) {
				logger.info("Access Token is empty");
				redirectAttributes.addFlashAttribute("error","Access token not valid"); 
				return null;
			}
			final ResponseEntity<CertificationCategory> response = certificationCategoryService.Get(authToken, id);
			if(response==null) {
				redirectAttributes.addFlashAttribute("error","Category not found");
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

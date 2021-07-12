package com.tcra.hrms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.configuration.HttpStatusCode;
import com.tcra.hrms.entity.RefereeEntity;
import com.tcra.hrms.services.IEmployeeInfoService;

@Controller
public class EmpRefereeController {
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private RefereeEntity referee;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@RequestMapping(value = "/referee.htm", method = RequestMethod.POST)
	public String referee(Model model,
			@ModelAttribute("referee") @Valid RefereeEntity refereeEntity,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.referee", bindingResult);
			redirectAttributes.addFlashAttribute("referee", new RefereeEntity());
		} else {
			String token = authenticationFacade.getAuthenticationToken();
			int empID = authenticationFacade.getUser().getEmpId();
						
			  refereeEntity.setEmployeeid(empID);
			  ResponseEntity<RefereeEntity> result = employeeInfoService.addReferee(refereeEntity, token);
			  
			  if (result != null) { 
				  int statusCode = result.getStatusCodeValue();
					
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
			  } else { 
				  redirectAttributes.addFlashAttribute("error","System Error"); 
			  }			 
		}

		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/employeeReferee.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody RefereeEntity getRefereeEntityID(@PathVariable("id")int id, HttpServletRequest request, Model model) {
		
		System.out.println("ID: " +id);
		String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<RefereeEntity> response = employeeInfoService.GetRefereeEntityByID(id, token);		
		referee = response.getBody();		
		System.out.println(referee);		
		return referee;
	}
	
	@RequestMapping(value = "/deleteReferees.htm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteReferees(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<RefereeEntity> response = employeeInfoService.deleteReferee(id, token);
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}
	
	@RequestMapping(value = "/editReferee.htm", method = RequestMethod.POST)
	public String editReferee(Model model,
			@ModelAttribute("referee") @Valid RefereeEntity refereeEntity,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.referee", bindingResult);
			redirectAttributes.addFlashAttribute("referee", new RefereeEntity());
		} else {
			String token = authenticationFacade.getAuthenticationToken();
			int empID = authenticationFacade.getUser().getEmpId();
						
			  refereeEntity.setEmployeeid(empID);
			  int id = refereeEntity.getId();
			  ResponseEntity<RefereeEntity> result = employeeInfoService.editRefereeEntity(id, refereeEntity, token);
			  
			  if (result != null) { 
				  int statusCode = result.getStatusCodeValue();
					
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
			  } else { 
				  redirectAttributes.addFlashAttribute("error","System Error"); 
			  }			 
		}

		return "redirect:/employee-personal-information.htm";
	}
	

}

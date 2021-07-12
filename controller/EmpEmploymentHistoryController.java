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
import com.tcra.hrms.entity.EmploymentHistoryEntity;
import com.tcra.hrms.entity.EmploymentHistoryEntityById;
import com.tcra.hrms.services.IEmployeeInfoService;

@Controller
public class EmpEmploymentHistoryController {

	@Autowired
	private EmploymentHistoryEntityById employmentHistoryEntityById;
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@RequestMapping(value = "/empoymentHistory.htm", method = RequestMethod.POST)
	public String employmentHistory(Model model,
			@ModelAttribute("employmentHistory") @Valid EmploymentHistoryEntity employmentHistoryEntity,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employmentHistory", bindingResult);
			redirectAttributes.addFlashAttribute("employmentHistory", new EmploymentHistoryEntity());
		} else {
			String token = authenticationFacade.getAuthenticationToken();
			int empID = authenticationFacade.getUser().getEmpId();
			String endDate = employmentHistoryEntity.getDate_end();
			if (endDate.isEmpty()) {
				employmentHistoryEntity.setDate_end("Up Todate");
			}		
			  employmentHistoryEntity.setEmployeeid(empID);
			  ResponseEntity<EmploymentHistoryEntity> result = employeeInfoService.addEmploymentHistory(employmentHistoryEntity, token);
			  
			  if (result != null) { 
				  int statusCode = result.getStatusCodeValue();
					
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
			  } else { 
				  redirectAttributes.addFlashAttribute("error","System Error"); 
			  }			 
		}

		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/editEmpoymentHistory.htm", method = RequestMethod.POST)
	public String editEmploymentHistory(Model model,
			@ModelAttribute("") @Valid EmploymentHistoryEntity employmentHistory,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employmentHistory", bindingResult);
			redirectAttributes.addFlashAttribute("employmentHistory", new EmploymentHistoryEntity());
		} else {
			String token = authenticationFacade.getAuthenticationToken();
			int empID = authenticationFacade.getUser().getEmpId();
			String endDate = employmentHistory.getDate_end();
			int id = employmentHistory.getId();
			if (endDate.isEmpty()) {
				employmentHistory.setDate_end("Up Todate");
			}		
			employmentHistory.setEmployeeid(empID);
			  ResponseEntity<EmploymentHistoryEntity> result = employeeInfoService.editEmploymentHistory(id, employmentHistory, token);
			  
			  if (result != null) { 
				  int statusCode = result.getStatusCodeValue();
					
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
			  } else { 
				  redirectAttributes.addFlashAttribute("error","System Error"); 
			  }			 
		}

		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/employeeHistory.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody EmploymentHistoryEntityById getEmploymenyHistoryID(@PathVariable("id")int id, HttpServletRequest request, Model model) {
		
		String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<EmploymentHistoryEntityById> response = employeeInfoService.GetEmploymentHistoryById(id, token);		
		employmentHistoryEntityById = response.getBody();	
		return employmentHistoryEntityById;
	}
	
	@RequestMapping(value = "/deleteEmploymnetHistory.htm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteEmploymnetHistory(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<EmploymentHistoryEntity> response = employeeInfoService.deleteEmploymentHistory(id, token);	
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}
	
	
}

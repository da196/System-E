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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.configuration.HttpStatusCode;
import com.tcra.hrms.entity.EmployeeAddressContactRequest;
import com.tcra.hrms.entity.EmployeeAddressContactResponse;
import com.tcra.hrms.services.IEmployeeInfoService;

@Controller
public class EmpContactAddressController {
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private EmployeeAddressContactResponse employeeAddressContact;

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@RequestMapping(value = "/contactAddress.htm", method = RequestMethod.POST)
	public String contactAddress(Model model,
			@ModelAttribute("contactAddress") @Valid EmployeeAddressContactRequest employeeAddressContactRequest,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		System.out.print("EmpContactAddress >> Create - ["+employeeAddressContactRequest.toString()+"]");
		
		if (bindingResult.hasErrors()) {
			//redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactAddress", bindingResult);
			//redirectAttributes.addFlashAttribute("contactAddress", new EmployeeAddressContactRequest());
			redirectAttributes.addFlashAttribute("error","Request Error"); 
		} else {
			 String token = authenticationFacade.getAuthenticationToken();
			 int empID = authenticationFacade.getUser().getEmpId();
						
			  try {
				  employeeAddressContactRequest.setEmployeeid(empID);
				  ResponseEntity<EmployeeAddressContactRequest> result = employeeInfoService.addEmployeeAddressContact(employeeAddressContactRequest, token);
				  
				  if (result != null) { 
					  int statusCode = result.getStatusCodeValue();
						
						HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
				  } else { 
					  redirectAttributes.addFlashAttribute("error","System Error"); 
				  }	
			  }catch(HttpClientErrorException ex) {
				  StringBuilder exceptionBuilder = new StringBuilder();
				  //exceptionBuilder.append("Exception - "+ex.toString());
				  //exceptionBuilder.append("Message - "+ex.getMessage());
				  //exceptionBuilder.append("Status - "+ex.getStatusText());
				  exceptionBuilder.append("Response is "+ex.getResponseBodyAsString());
				  //ex.printStackTrace();
				  
				  redirectAttributes.addFlashAttribute("error",exceptionBuilder.toString()); 
			  }	catch(Exception ex) {
				  StringBuilder exceptionBuilder = new StringBuilder();
				  exceptionBuilder.append("Exception - "+ex.toString());
				  //exceptionBuilder.append("Message - "+ex.getMessage());
				  //exceptionBuilder.append("Cause - "+ex.getCause()!=null?ex.getCause().toString():"NIL");
				  //ex.printStackTrace();
				  
				  redirectAttributes.addFlashAttribute("error",exceptionBuilder.toString()); 
			  }		 
		}

		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/editContactAddress.htm", method = RequestMethod.POST)
	public String editContactAddress(Model model,
			@ModelAttribute("contactAddressEdit") @Valid EmployeeAddressContactRequest employeeAddressContactRequest,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("error","Request Error"); 
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactAddress", bindingResult);
			redirectAttributes.addFlashAttribute("contactAddress", new EmployeeAddressContactRequest());
		} else {
			 String token = authenticationFacade.getAuthenticationToken();
			int empID = authenticationFacade.getUser().getEmpId();
			int addressID = employeeAddressContactRequest.getAdressid();
			int contactID = employeeAddressContactRequest.getContactid();
			  employeeAddressContactRequest.setEmployeeid(empID);
			  ResponseEntity<EmployeeAddressContactRequest> result = employeeInfoService.editEmployeeAddressContact(addressID, contactID, employeeAddressContactRequest, token);
			  
			  if (result != null) { 
				  int statusCode = result.getStatusCodeValue();
					
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
			  } else { 
				  redirectAttributes.addFlashAttribute("error","System Error"); 
			  }			 
		}

		return "redirect:/employee-personal-information.htm";
	}
	
	
	@RequestMapping(value = "/employeeContactAddress.htm/{addressID}/{contactID}", method = RequestMethod.GET)
	public @ResponseBody EmployeeAddressContactResponse getContactAddresID(@PathVariable("addressID")int addressID, @PathVariable("contactID")int contactID, HttpServletRequest request, Model model) {
	
		 String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<EmployeeAddressContactResponse> response = employeeInfoService.GetEmployeeAddressContactByID(addressID, contactID, token);
		employeeAddressContact = response.getBody();		
		return employeeAddressContact;
	}
	
	@RequestMapping(value = "/deleteContactAddress.htm/{addressID}/{contactID}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteContactAddress(@PathVariable("addressID")int addressID, @PathVariable("contactID")int contactID, 
			HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		 String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<EmployeeAddressContactResponse> response = employeeInfoService.deleteEmployeeAddressContact(addressID, contactID, token);
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}

}

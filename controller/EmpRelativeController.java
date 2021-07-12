package com.tcra.hrms.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpStatusCode;
import com.tcra.hrms.configuration.Utility;
import com.tcra.hrms.entity.EmployeeRelativeResponse;
import com.tcra.hrms.entity.EmployeeRelatives;
import com.tcra.hrms.services.IEmployeeInfoService;

@Controller
public class EmpRelativeController {
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private EmployeeRelativeResponse employeeRelativeResp;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@RequestMapping(value = "/relatives.htm", method = RequestMethod.POST)
	public String relatives(Model model,
			@ModelAttribute("relatives") @Valid EmployeeRelatives employeeRelatives, @RequestParam("fileUpload") MultipartFile fileUpload,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();

		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		employeeRelatives.setEmployeeid(empID);
		employeeRelatives.setAttachmenttypeid(2);
		if (fileUpload.isEmpty()) {
			 ResponseEntity<EmployeeRelatives> result = employeeInfoService.addEmployeeRelatives(employeeRelatives, token);
				
				if (result != null) {
					int statusCode = result.getStatusCodeValue();					
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
					
				} else {
					redirectAttributes.addFlashAttribute("error", "System Error");
				}			
		} else {
					
			String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if(extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png") || extensionType.toLowerCase().equals("jpeg")) {
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;
				
				System.out.println(fileURL);
				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				
				employeeRelatives.setAttachmentname(fileName);
				
				if (isFileUpload == true) {
					employeeRelatives.setUri(AppConstants.URL + newFileName);
					  ResponseEntity<EmployeeRelatives> result = employeeInfoService.addEmployeeRelatives(employeeRelatives, token);
					
					if (result != null) {
						int statusCode = result.getStatusCodeValue();
						
						HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
						
					} else {
						redirectAttributes.addFlashAttribute("error", "System Error");
					}
				} else {
					redirectAttributes.addFlashAttribute("error", "Error File Upload");		
				}
			}else {
				redirectAttributes.addFlashAttribute("error", "File Format Not Allowed");	
			}
			
		}
		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/editRelatives.htm", method = RequestMethod.POST)
	public String editRelatives(Model model,
			@ModelAttribute("relatives") @Valid EmployeeRelatives employeeRelatives, @RequestParam("fileUpload") MultipartFile fileUpload,
			BindingResult bindingResult,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		int relID = employeeRelatives.getId();
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();
		employeeRelatives.setEmployeeid(empID);
		System.out.println(employeeRelatives.getPhoneprimary());
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		
		if (fileUpload.isEmpty()) {
			
			  ResponseEntity<EmployeeRelatives> result = employeeInfoService.editEmployeeRelatives(relID, employeeRelatives, token);
			 
				if (result != null) {
					int statusCode = result.getStatusCodeValue();
					System.out.println(statusCode);
					HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
					
				} else {
					redirectAttributes.addFlashAttribute("error", "System Error");
				}		
		} else {
					
			String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if(extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png") || extensionType.toLowerCase().equals("jpeg")) {
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;
				
				System.out.println(fileURL);
				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				
				employeeRelatives.setAttachmentname(fileName);
				employeeRelatives.setAttachmenttypeid(2);

				if (isFileUpload == true) {
					 employeeRelatives.setUri(AppConstants.URL + newFileName);
					 ResponseEntity<EmployeeRelatives> result = employeeInfoService.editEmployeeRelatives(relID, employeeRelatives, token);
					
					if (result != null) {
						int statusCode = result.getStatusCodeValue();
						
						HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
						
					} else {
						redirectAttributes.addFlashAttribute("error", "System Error");
					}
				} else {
					redirectAttributes.addFlashAttribute("error", "Error File Upload");		
				}
			}else {
				redirectAttributes.addFlashAttribute("error", "File Format Not Allowed");	
			}
			
		}
		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/employeeRelatives.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody EmployeeRelativeResponse getRelativesID(@PathVariable("id")int id, HttpServletRequest request, Model model) {
		
		System.out.println("ID: " +id);
		String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<EmployeeRelativeResponse> response = employeeInfoService.GeEmployeeRelativeByID(id, token);
		employeeRelativeResp = response.getBody();		
		System.out.println(employeeRelativeResp);
		return employeeRelativeResp;
	}
	
	@RequestMapping(value = "/deleteRelatives.htm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteRelatives(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<EmployeeRelativeResponse> response = employeeInfoService.deleteEmployeeRelatives(id, token);
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}
}

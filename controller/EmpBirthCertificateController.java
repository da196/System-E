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
import com.tcra.hrms.entity.EmployeeBirthDetailsRequest;
import com.tcra.hrms.entity.EmployeeBirthDetailsResponse;
import com.tcra.hrms.services.IEmployeeInfoService;

@Controller
public class EmpBirthCertificateController {
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private EmployeeBirthDetailsResponse employeeBirthDetails;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	

	@RequestMapping(value = "/birthCertificate.htm", method = RequestMethod.POST)
	public String birthCertificate(Model model,
			@ModelAttribute("birthCertificate") @Valid EmployeeBirthDetailsRequest employeeBirthDetailsRequest,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();

		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		
		if (fileUpload.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Please Attach Birth Certificate");			
		} else {
					
			String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if(extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png") || extensionType.toLowerCase().equals("jpeg")) {
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;
				
				System.out.println(fileURL);
				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				
				employeeBirthDetailsRequest.setEmployeeid(empID);
				employeeBirthDetailsRequest.setAttachmentname(fileName);
				employeeBirthDetailsRequest.setAttachmenttypeid(2);

				if (isFileUpload == true) {
					employeeBirthDetailsRequest.setUri(AppConstants.URL + newFileName);
					ResponseEntity<EmployeeBirthDetailsRequest> result = employeeInfoService.addEmployeeBirthDetails(employeeBirthDetailsRequest, token);
					
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
	
	
	@RequestMapping(value = "/editBirthCertificate.htm", method = RequestMethod.POST)
	public String editBirthCertificate(Model model,
			@ModelAttribute("birthCertificate") @Valid EmployeeBirthDetailsRequest employeeBirthDetailsRequest,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		int birthID = employeeBirthDetailsRequest.getId();
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();

		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		employeeBirthDetailsRequest.setEmployeeid(empID);	
		employeeBirthDetailsRequest.setId(birthID);
		
		System.out.println("EmpBirthDetailsController >> "+employeeBirthDetailsRequest.toString());
		
		if (fileUpload.isEmpty()) {
			ResponseEntity<EmployeeBirthDetailsRequest> result = employeeInfoService.editEmployeeBirthDetails(birthID, employeeBirthDetailsRequest, token);
			
			if (result != null) {
				int statusCode = result.getStatusCodeValue();
				
				HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
				
			} else {
				redirectAttributes.addFlashAttribute("error", "System Error");
			}			
		} else {
					
			String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if(extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png") || extensionType.toLowerCase().equals("jpeg")) {
				
			}else {
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;
				
				System.out.println(fileURL);
				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				
				//employeeBirthDetailsRequest.setEmployeeid(empID);
				employeeBirthDetailsRequest.setAttachmentname(fileName);
				//employeeBirthDetailsRequest.setAttachmenttypeid(2);

				if (isFileUpload == true) {
					employeeBirthDetailsRequest.setUri(AppConstants.URL + newFileName);
					ResponseEntity<EmployeeBirthDetailsRequest> result = employeeInfoService.editEmployeeBirthDetails(birthID, employeeBirthDetailsRequest, token);
					
					if (result != null) {
						int statusCode = result.getStatusCodeValue();
						
						HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
						
					} else {
						redirectAttributes.addFlashAttribute("error", "System Error");
					}
				} else {
					redirectAttributes.addFlashAttribute("error", "Error File Upload");		
				}
			}
			
		}
		return "redirect:/employee-personal-information.htm";
	}
	
	@RequestMapping(value = "/deleteBirthCertificate.htm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteBirthCertificate(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<EmployeeBirthDetailsRequest> response = employeeInfoService.deleteEmployeeBirthDetails(id, token);
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}
	
	@RequestMapping(value = "/employeeBirthCertificate.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody EmployeeBirthDetailsResponse getEmployeeBirthDetailsID(@PathVariable("id")int id, HttpServletRequest request, Model model) {
	
		String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<EmployeeBirthDetailsResponse> response = employeeInfoService.GetEmployeeBirthDetailsByID(id, token);
		employeeBirthDetails = response.getBody();		
		return employeeBirthDetails;
	}
}

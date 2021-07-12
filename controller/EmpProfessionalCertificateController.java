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
import com.tcra.hrms.entity.EmployeeProfessionalCertificationResponse;
import com.tcra.hrms.entity.EmployeeProffesionalCertification;
import com.tcra.hrms.services.IEmployeeInfoService;

@Controller
public class EmpProfessionalCertificateController {
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private EmployeeProfessionalCertificationResponse employeeProfessionalCertification;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@RequestMapping(value = "/professionalCertificate.htm", method = RequestMethod.POST)
	public String professionalEducation(Model model,
			@ModelAttribute("professionalCertificate") @Valid EmployeeProffesionalCertification employeeProffesionalCertification,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();

		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		
		if (fileUpload.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "System Error");			
		} else {
					
			String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
			if(extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png") || extensionType.toLowerCase().equals("jpeg")) {
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;
			
				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				
				employeeProffesionalCertification.setEmployeeid(empID);
				employeeProffesionalCertification.setAttachmentname(fileName);
				employeeProffesionalCertification.setAttachmenttypeid(4);
				if (isFileUpload == true) {
					System.out.println(AppConstants.URL + newFileName);
					employeeProffesionalCertification.setUri(AppConstants.URL + newFileName);
					ResponseEntity<EmployeeProffesionalCertification> result = employeeInfoService.addEmployeeProffesionalCertification(employeeProffesionalCertification, token);
					
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
	
	@RequestMapping(value = "/editProfessionalCertificate.htm", method = RequestMethod.POST)
	public String editProfessionalEducation(Model model,
			@ModelAttribute("professionalCertificate") @Valid EmployeeProfessionalCertificationResponse employeeProffesionalCertification,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		int profID = employeeProffesionalCertification.getId();

		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();

		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		employeeProffesionalCertification.setEmployeeid(empID);
		
		if (fileUpload.isEmpty()) {
			
			ResponseEntity<EmployeeProfessionalCertificationResponse> result = employeeInfoService.editProffesionalCertification(profID, employeeProffesionalCertification, token);
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
			
				boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
				
				//employeeProffesionalCertification.setEmployeeid(empID);
				employeeProffesionalCertification.setAttachmentname(fileName);
				//employeeProffesionalCertification.setAttachmenttypeid(3);
				if (isFileUpload == true) {
					System.out.println(AppConstants.URL + newFileName);
					employeeProffesionalCertification.setUri(AppConstants.URL + newFileName);
					ResponseEntity<EmployeeProfessionalCertificationResponse> result = employeeInfoService.editProffesionalCertification(profID, employeeProffesionalCertification, token);
					
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
	
	
	@RequestMapping(value = "/employeeProfessionalCertificate.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody EmployeeProfessionalCertificationResponse getProfessionalCertificateID(@PathVariable("id")int id, HttpServletRequest request, Model model) {
		
		System.out.println("ID: " +id);
		String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<EmployeeProfessionalCertificationResponse> response = employeeInfoService.GetEmployeeProfessionalCertificationByID(id, token);		
		employeeProfessionalCertification = response.getBody();				
		return employeeProfessionalCertification;
	}
	
	@RequestMapping(value = "/deleteProfessionalCertificate.htm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteProfessionalCertificate(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<EmployeeProfessionalCertificationResponse> response = employeeInfoService.deleteProffesionalCertification(id, token);
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}
	


}

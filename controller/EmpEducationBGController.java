package com.tcra.hrms.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

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
import com.tcra.hrms.entity.EducationCourseEntity;
import com.tcra.hrms.entity.EmployeeEducationBGEntity;
import com.tcra.hrms.entity.EmployeeEducationResponse;
import com.tcra.hrms.services.IEmployeeInfoService;
import com.tcra.hrms.services.ILookupService;

@Controller
public class EmpEducationBGController {
	
	
	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private EmployeeEducationResponse employeeEducationResponse;
	
	@Autowired
	private ILookupService lookupService;
	
	@Autowired
	private List<EducationCourseEntity> educationCourseEntity;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@RequestMapping(value = "/educationBackground.htm", method = RequestMethod.POST)
	public String education(Model model,
			@ModelAttribute("educationBackground") @Valid EmployeeEducationBGEntity employeeEducationBGEntity,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();
		
		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
			 double bytes = fileUpload.getSize();
	         double kilobytes = (bytes / 1024);
			 double megabytesFileSize = (kilobytes / 1024);
			 System.out.println("megabytesFileSize: " + megabytesFileSize);
		if (fileUpload.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Please attach certificate");			
		} else {
					
			String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
				if(extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png") || extensionType.toLowerCase().equals("jpeg")) {
					String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
					String newFileName = fileName + "_" + id + "." + extensionType;
					String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;
					boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
					employeeEducationBGEntity.setEmployeeid(empID);
					employeeEducationBGEntity.setAttachmentname(fileName);

					if (isFileUpload == true) {
						employeeEducationBGEntity.setUri(AppConstants.URL + newFileName);
						employeeEducationBGEntity.setAttachmenttypeid(3);
						ResponseEntity<EmployeeEducationBGEntity> result = employeeInfoService.addEmployeeEducationBG(employeeEducationBGEntity, token);
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
	
	@RequestMapping(value = "/editEducationBackground.htm", method = RequestMethod.POST)
	public String editEducation(Model model,
			@ModelAttribute("educationBackground") @Valid EmployeeEducationBGEntity employeeEducationBGEntity,
			BindingResult bindingResult, @RequestParam("fileUpload") MultipartFile fileUpload,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		int eduID = employeeEducationBGEntity.getId();
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();

		Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
		Long id = timestamp2.getTime();
		employeeEducationBGEntity.setEmployeeid(empID);

		if (fileUpload.isEmpty()) {
		
			ResponseEntity<EmployeeEducationBGEntity> result = employeeInfoService.editEmployeeEducationBG(eduID,employeeEducationBGEntity, token);
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

				employeeEducationBGEntity.setAttachmentname(fileName);

				if (isFileUpload == true) {
					employeeEducationBGEntity.setUri(AppConstants.URL + newFileName);
					//employeeEducationBGEntity.setAttachmenttypeid(2);
					
					ResponseEntity<EmployeeEducationBGEntity> result = employeeInfoService.editEmployeeEducationBG(eduID,employeeEducationBGEntity, token);
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

	@RequestMapping(value = "/employee-education-bg.htm/{id}", method = RequestMethod.GET)
	public @ResponseBody EmployeeEducationResponse getEducationBackgroundID(@PathVariable("id")int id, HttpServletRequest request, Model model) {
		
		String token = authenticationFacade.getAuthenticationToken();
		ResponseEntity<EmployeeEducationResponse> responseEducationBG = employeeInfoService.GetEmployeeEducationBGByID(id, token);		
		employeeEducationResponse = responseEducationBG.getBody();		
		return employeeEducationResponse;
	}
	
	@RequestMapping(value = "/deleteEmployeeeducation.htm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody int deleteEducationBackgroundID(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();
		
		ResponseEntity<EmployeeEducationBGEntity> response = employeeInfoService.deleteEmploymentEducation(id, token);	
		int statusCode = response.getStatusCodeValue();
		
		HttpStatusCode.getStatusRedirect(statusCode, redirectAttributes);
		
		return statusCode;
	}
	
	@RequestMapping(value = "/getEducationCourseByLevelCode/{id}", method = RequestMethod.GET)
	public @ResponseBody List<EducationCourseEntity> getEducationCourseByLevelCode(@PathVariable("id")int id, HttpServletRequest request, Model model, 
			RedirectAttributes redirectAttributes) {
		
		String token = authenticationFacade.getAuthenticationToken();		
		ResponseEntity<EducationCourseEntity[]> response = lookupService.GetEducationCourses(id, token);		
		educationCourseEntity = Arrays.asList(response.getBody());
		
		return educationCourseEntity;
	}
}

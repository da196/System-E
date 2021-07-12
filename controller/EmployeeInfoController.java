package com.tcra.hrms.controller;


import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.dto.EducationInstitutionCategory;
import com.tcra.hrms.dto.EducationInstitutionType;
import com.tcra.hrms.dto.ShortCourse;
import com.tcra.hrms.entity.AttachmentTypeEntity;
import com.tcra.hrms.entity.CertificationCategoryEntity;
import com.tcra.hrms.entity.CityEntity;
import com.tcra.hrms.entity.ContactTypeEntity;
import com.tcra.hrms.entity.CountryEntity;
import com.tcra.hrms.entity.EducationCourseEntity;
import com.tcra.hrms.entity.EducationInstitutionEntity;
import com.tcra.hrms.entity.EducationLevelEntity;
import com.tcra.hrms.entity.EmployeeAddressContactResponse;
import com.tcra.hrms.entity.EmployeeBirthDetailsResponse;
import com.tcra.hrms.entity.EmployeeEducationBGEntityV2;
import com.tcra.hrms.entity.EmployeeEntity;
import com.tcra.hrms.entity.EmployeeProfessionalCertificationResponse;
import com.tcra.hrms.entity.EmployeeRelativeCategoryEntity;
import com.tcra.hrms.entity.EmployeeRelativeResponse;
import com.tcra.hrms.entity.EmploymentHistoryByEmpId;
import com.tcra.hrms.entity.LocationAddressEntity;
import com.tcra.hrms.entity.NationalityEntity;
import com.tcra.hrms.entity.RefereeCategoryEntity;
import com.tcra.hrms.entity.RefereeEntity;
import com.tcra.hrms.entity.YearEntity;
import com.tcra.hrms.services.IEducationInstitutionCategoryService;
import com.tcra.hrms.services.IEducationInstitutionTypeService;
import com.tcra.hrms.services.IEmployeeInfoService;
import com.tcra.hrms.services.ILoginServices;
import com.tcra.hrms.services.ILookupService;
import com.tcra.hrms.services.IShortCourseService;

@Controller
public class EmployeeInfoController {

	// services
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private ILoginServices loginServices;

	@Autowired
	private EmployeeEntity employeeEntity;

	@Autowired
	private IEmployeeInfoService employeeInfoService;
	
	@Autowired
	private ILookupService lookupService;
	
	@Autowired
	private IShortCourseService shortCourseService;
	
	@Autowired
	private IEducationInstitutionTypeService educationInstitutionTypeService;
	
	@Autowired
	private IEducationInstitutionCategoryService educationInstitutionCategoryService;

	// entities
	@Autowired
	private List<EducationLevelEntity> educationLevelEntity;

	@Autowired
	private List<EducationCourseEntity> educationCourseEntity;

	@Autowired
	private List<EducationInstitutionEntity> educationInstitutionEntity;
	
	@Autowired
	private List<EducationInstitutionType> educationInstitutionTypes;
	
	@Autowired
	private List<EducationInstitutionCategory> educationInstitutionCategories;

	@Autowired
	private List<YearEntity> yearEntity;

	@Autowired
	private List<AttachmentTypeEntity> attachmentTypeEntity;

	@Autowired
	private List<CountryEntity> countryEntity;
	
	@Autowired
	private List<EmployeeEducationBGEntityV2> employeeEducationBGEntityV2;
	
	//@Autowired
	private List<ShortCourse> employeeShortCourses;

	@Autowired
	private List<EmploymentHistoryByEmpId> employmentHistoryByEmpId;
	
	@Autowired
	private List<NationalityEntity> nationalityEntity;
	
	@Autowired
	private List<RefereeCategoryEntity> refereeCategoryEntity;
	
	@Autowired
	private List<RefereeEntity> refereeEntity;

	@Autowired
	private List<CertificationCategoryEntity> certificationCategoryEntity;
	
	@Autowired
	private List<EmployeeProfessionalCertificationResponse> employeeProfessionalCertificationResponse;
	
	@Autowired
	private List<EmployeeRelativeResponse> employeeRelative;
	
	
	@Autowired
	private List<EmployeeRelativeCategoryEntity> employeeRelativeCategory;
	
	@Autowired
	private EmployeeBirthDetailsResponse employeeBirthDetails;
	
	@Autowired
	private List<LocationAddressEntity> locationAddressEntity;
	
	@Autowired
	private List<CityEntity> cityEntity;
	
	@Autowired
	private List<ContactTypeEntity> contactTypeEntity;
	
	@Autowired
	private List<EmployeeAddressContactResponse> employeeAddressContactResponse;

	@RequestMapping(value = { "/employee-personal-information.htm" }) 
	public String  LoadEmpInfo(HttpServletRequest request, Model model) {  
		  try {			  
			  loadLookup(request, model);
			  return "emp-personal-info"; 
		  }catch(Exception er) {
			  er.printStackTrace();
			  return "redirect:/";
		  }
	}
	 
	/*
	 * private void uploadUserAttributesToSession(ResponseEntity<UserEntity> result,
	 * HttpServletRequest request, Model model) {
	 * request.getSession().setAttribute("email", result.getBody().getEmail());
	 * request.getSession().setAttribute("fullname",
	 * result.getBody().getFullname());
	 * request.getSession().setAttribute("filenumber",
	 * result.getBody().getFilenumber()); request.getSession().setAttribute("empId",
	 * result.getBody().getEmpId()); request.getSession().setAttribute("token",
	 * result.getHeaders().getFirst("Authorization"));
	 * 
	 * //loadLookup(request, model);
	 * 
	 * }
	 */

	private void loadLookup(HttpServletRequest request, Model model) {
	
		String token = authenticationFacade.getAuthenticationToken();
		String email = authenticationFacade.getUser().getEmail().trim();
		int empID = authenticationFacade.getUser().getEmpId();
		//System.out.println("Employee ID: " + empID);

		ResponseEntity<EmployeeEntity> response = loginServices.GetEmployeeGeneralInfo(token, email);
		ResponseEntity<EducationLevelEntity[]> responseLevel = lookupService.GetEducationLevel(token);
		ResponseEntity<EducationCourseEntity[]> responseCourse = lookupService.GetEducationCourses(token);
		
		ResponseEntity<EducationInstitutionEntity[]> responseInstitution = lookupService.GetEducationInstitution(token);
		ResponseEntity<EducationInstitutionType[]> responseInstitutionTypes = educationInstitutionTypeService.GetAll(token);
		ResponseEntity<EducationInstitutionCategory[]> responseInstitutionCategories = educationInstitutionCategoryService.GetAll(token);
		
		ResponseEntity<YearEntity[]> responseYear = lookupService.GetYear(token);
		ResponseEntity<AttachmentTypeEntity[]> responseAttachment = lookupService.GetAttachmentType(token);
		ResponseEntity<CountryEntity[]> responseCountry = lookupService.GetCountry(token);
		ResponseEntity<NationalityEntity[]> responseNationality = lookupService.GetNationality(token);
		ResponseEntity<RefereeCategoryEntity[]> responseRefereeCategory = lookupService.GetRefereeCategory(token);
		ResponseEntity<CertificationCategoryEntity[]> responseCertificationCategory = lookupService.GetCertificationCategory(token);
		ResponseEntity<EmployeeRelativeCategoryEntity[]> responseRelativeCategory = lookupService.GetEmployeeRelativeCategoryEntity(token);
		
		ResponseEntity<LocationAddressEntity[]> responseLocation = lookupService.GetLocationAddressEntity(token);
		ResponseEntity<CityEntity[]> responseCity = lookupService.GetCityEntity(token);
		ResponseEntity<ContactTypeEntity[]> responseContactType = lookupService.GetContactTypeEntity(token);
		
		
		ResponseEntity<EmployeeEducationBGEntityV2[]> responseEducationBG = employeeInfoService.GetEmployeeEducationBG(token,  Integer.toString(empID));
		ResponseEntity<ShortCourse[]> responseShortCourses = shortCourseService.GetAll(token, empID);
		
		ResponseEntity<EmploymentHistoryByEmpId[]> responseEmploymentHistory = employeeInfoService.GetEmploymentHistoryEmpID(token, Integer.toString(empID));
		ResponseEntity<RefereeEntity[]> responseRefereeEntity = employeeInfoService.GetRefereeEntityEmpID(token, Integer.toString(empID));
		ResponseEntity<EmployeeProfessionalCertificationResponse[]> responseProfessionalEntity = employeeInfoService.GetEmployeeProffesionalCertification(token, Integer.toString(empID));
		ResponseEntity<EmployeeRelativeResponse[]> responseRelatives = employeeInfoService.GetEmployeeRelatives(token, Integer.toString(empID));
		ResponseEntity<EmployeeBirthDetailsResponse> responseBirthDetails = employeeInfoService.GetEmployeeBirthDetails(token, Integer.toString(empID));
		ResponseEntity<EmployeeAddressContactResponse[]> responseContactAddress = employeeInfoService.GetEmployeeAddressContact(token, Integer.toString(empID));
		
		
		System.out.println(response.getBody());
		employeeEntity = response.getBody();
		educationLevelEntity = Arrays.asList(responseLevel.getBody());
		educationCourseEntity = Arrays.asList(responseCourse.getBody());
		educationInstitutionEntity = Arrays.asList(responseInstitution.getBody());
		educationInstitutionTypes = Arrays.asList(responseInstitutionTypes.getBody());
		educationInstitutionCategories = Arrays.asList(responseInstitutionCategories.getBody());
		yearEntity = Arrays.asList(responseYear.getBody());
		attachmentTypeEntity = Arrays.asList(responseAttachment.getBody());
		countryEntity = Arrays.asList(responseCountry.getBody());
		nationalityEntity = Arrays.asList(responseNationality.getBody());
		refereeCategoryEntity = Arrays.asList(responseRefereeCategory.getBody());
		certificationCategoryEntity = Arrays.asList(responseCertificationCategory.getBody());
		employeeRelativeCategory = Arrays.asList(responseRelativeCategory.getBody());
		locationAddressEntity = Arrays.asList(responseLocation.getBody());
		cityEntity = Arrays.asList(responseCity.getBody());
		contactTypeEntity = Arrays.asList(responseContactType.getBody());
		
		model.addAttribute("employeeEntity", employeeEntity);
		
		model.addAttribute("locationAddress", locationAddressEntity);
		model.addAttribute("city", cityEntity);
		model.addAttribute("contactType", contactTypeEntity);
		
		model.addAttribute("educationLevelEntity", educationLevelEntity);
		model.addAttribute("educationCourseEntity", educationCourseEntity);
		model.addAttribute("educationInstitutionEntity", educationInstitutionEntity);
		model.addAttribute("educationInstitutionTypes", educationInstitutionTypes);
		model.addAttribute("educationInstitutionCategories", educationInstitutionCategories);
		model.addAttribute("yearEntity", yearEntity);
		model.addAttribute("yearEntity", yearEntity);
		model.addAttribute("attachmentTypeEntity", attachmentTypeEntity);
		model.addAttribute("countryEntity", countryEntity);
		model.addAttribute("nationalityEntity", nationalityEntity);
		model.addAttribute("refereeCategoryEntity", refereeCategoryEntity);
		model.addAttribute("certificationCategoryEntity", certificationCategoryEntity);
		model.addAttribute("employeeRelativeCategory", employeeRelativeCategory);		
		
		if(responseEducationBG != null ) {
			employeeEducationBGEntityV2 = Arrays.asList(responseEducationBG.getBody());
		    model.addAttribute("employeeEducationBGEntity", employeeEducationBGEntityV2);
		}if(responseShortCourses != null ) {
			employeeShortCourses = Arrays.asList(responseShortCourses.getBody());
		    model.addAttribute("employeeShortCourses", employeeShortCourses);		    	
		}if(responseEmploymentHistory != null) {
			employmentHistoryByEmpId = Arrays.asList(responseEmploymentHistory.getBody());
			model.addAttribute("employmentHistoryByEmpId", employmentHistoryByEmpId);
		}if (responseRefereeEntity != null) {
			refereeEntity = Arrays.asList(responseRefereeEntity.getBody());
			model.addAttribute("refereeEntity", refereeEntity);
		}if(responseProfessionalEntity != null){ 
			employeeProfessionalCertificationResponse = Arrays.asList(responseProfessionalEntity.getBody());
			model.addAttribute("employeeProfessional", employeeProfessionalCertificationResponse);
		}if(responseRelatives != null) { 
			employeeRelative =Arrays.asList(responseRelatives.getBody());
			model.addAttribute("employeeRelatives",employeeRelative);
		}if(responseBirthDetails != null){  
			employeeBirthDetails =responseBirthDetails.getBody();
			model.addAttribute("employeeBirthDetails",employeeBirthDetails);
		}if(responseContactAddress != null){  
			employeeAddressContactResponse = Arrays.asList(responseContactAddress.getBody());
			model.addAttribute("employeeAddressContactResponse", employeeAddressContactResponse);
		}else {
			
		}
	}

}

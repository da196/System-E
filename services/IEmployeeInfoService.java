package com.tcra.hrms.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.entity.EmployeeAddressContactRequest;
import com.tcra.hrms.entity.EmployeeAddressContactResponse;
import com.tcra.hrms.entity.EmployeeBirthDetailsRequest;
import com.tcra.hrms.entity.EmployeeBirthDetailsResponse;
import com.tcra.hrms.entity.EmployeeEducationBGEntity;
import com.tcra.hrms.entity.EmployeeEducationBGEntityV2;
import com.tcra.hrms.entity.EmployeeEducationResponse;
import com.tcra.hrms.entity.EmployeeEntity;
import com.tcra.hrms.entity.EmployeeProfessionalCertificationResponse;
import com.tcra.hrms.entity.EmployeeProffesionalCertification;
import com.tcra.hrms.entity.EmployeeRelativeResponse;
import com.tcra.hrms.entity.EmployeeRelatives;
import com.tcra.hrms.entity.EmploymentHistoryByEmpId;
import com.tcra.hrms.entity.EmploymentHistoryEntity;
import com.tcra.hrms.entity.EmploymentHistoryEntityById;
import com.tcra.hrms.entity.RefereeEntity;;

@Component
public interface IEmployeeInfoService {
	

	
	
	//Employee Education Background
	ResponseEntity<EmployeeEducationBGEntity> addEmployeeEducationBG(EmployeeEducationBGEntity employeeEducationBGEntity,String token);
	ResponseEntity<EmployeeEducationResponse> GetEmployeeEducationBGByID(int id,String token);	
	ResponseEntity<EmployeeEducationBGEntityV2[]> GetEmployeeEducationBG(String token, String empID);	
	ResponseEntity<EmployeeEducationBGEntity> deleteEmploymentEducation(int id,String token);
	ResponseEntity<EmployeeEducationBGEntity> editEmployeeEducationBG(int id,EmployeeEducationBGEntity employeeEducationBGEntity,String token);
	 
	
	//Employment History
	ResponseEntity<EmploymentHistoryEntity> addEmploymentHistory(EmploymentHistoryEntity employmentHistoryEntity,String token);
	ResponseEntity<EmploymentHistoryByEmpId[]> GetEmploymentHistoryEmpID(String token, String empID);
	ResponseEntity<EmploymentHistoryEntityById> GetEmploymentHistoryById(int id,String token);
	ResponseEntity<EmploymentHistoryEntity> deleteEmploymentHistory(int id,String token);
	ResponseEntity<EmploymentHistoryEntity> editEmploymentHistory(int id,EmploymentHistoryEntity employmentHistory,String token);
	
	//Referee
	ResponseEntity<RefereeEntity> addReferee(RefereeEntity refereeEntity,String token);
	ResponseEntity<RefereeEntity[]> GetRefereeEntityEmpID(String token, String empID);
	ResponseEntity<RefereeEntity> GetRefereeEntityByID(int id,String token);
	ResponseEntity<RefereeEntity> deleteReferee(int id,String token);
	ResponseEntity<RefereeEntity> editRefereeEntity(int id,RefereeEntity refereeEntity,String token);
	
	//Employee Professional Certificate
	ResponseEntity<EmployeeProffesionalCertification> addEmployeeProffesionalCertification(EmployeeProffesionalCertification employeeProffesionalCertification,String token);
	ResponseEntity<EmployeeProfessionalCertificationResponse[]> GetEmployeeProffesionalCertification(String token, String empID);
	ResponseEntity<EmployeeProfessionalCertificationResponse> GetEmployeeProfessionalCertificationByID(int id,String token);
	ResponseEntity<EmployeeProfessionalCertificationResponse> deleteProffesionalCertification(int id,String token);
	ResponseEntity<EmployeeProfessionalCertificationResponse> editProffesionalCertification(int id,EmployeeProfessionalCertificationResponse employeeProffesionalCertification,String token);
	
	//Employee Relatives
	ResponseEntity<EmployeeRelatives> addEmployeeRelatives(EmployeeRelatives employeeRelatives,String token);
	ResponseEntity<EmployeeRelativeResponse[]> GetEmployeeRelatives(String token, String empID);
	ResponseEntity<EmployeeRelativeResponse> GeEmployeeRelativeByID(int id,String token);
	ResponseEntity<EmployeeRelativeResponse> deleteEmployeeRelatives(int id,String token);
	ResponseEntity<EmployeeRelatives> editEmployeeRelatives(int id,EmployeeRelatives employeeRelative,String token);
	
	//Employee Birth Certificate 
	ResponseEntity<EmployeeBirthDetailsRequest> addEmployeeBirthDetails(EmployeeBirthDetailsRequest employeeBirthDetailsRequest, String token);
	ResponseEntity<EmployeeBirthDetailsResponse> GetEmployeeBirthDetails(String token, String empID);
	ResponseEntity<EmployeeBirthDetailsResponse> GetEmployeeBirthDetailsByID(int id, String token);
	ResponseEntity<EmployeeBirthDetailsRequest> deleteEmployeeBirthDetails(int id,String token);
	ResponseEntity<EmployeeBirthDetailsRequest> editEmployeeBirthDetails(int id,EmployeeBirthDetailsRequest employeeBirthDetailsRequest,String token);
	
	//Contact Address
	ResponseEntity<EmployeeAddressContactRequest> addEmployeeAddressContact(EmployeeAddressContactRequest employeeAddressContactRequest, String token);
	ResponseEntity<EmployeeAddressContactResponse[]> GetEmployeeAddressContact(String token,  String empID);
	ResponseEntity<EmployeeAddressContactResponse> GetEmployeeAddressContactByID(int addressID, int contactID,  String token);
	ResponseEntity<EmployeeAddressContactResponse> deleteEmployeeAddressContact(int addressID, int contactID,  String token);
	ResponseEntity<EmployeeAddressContactRequest> editEmployeeAddressContact(int addressID, int contactID, EmployeeAddressContactRequest employeeAddressContactRequest, String token);
	Object addEmployeeContactAddress(EmployeeAddressContactRequest employeeAddressContactRequest, String token);
	
	//Upload Passport Size
	
	ResponseEntity<EmployeeEntity> uploadEmployeePassportSize(String token, EmployeeEntity employeeEntity);


}

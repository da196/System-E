package com.tcra.hrms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
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
import com.tcra.hrms.entity.RefereeEntity;


@Component
public class EmployeeInfoService implements IEmployeeInfoService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate; //restTemplate
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	

	
	@Override
	public ResponseEntity<EmployeeEducationBGEntity> addEmployeeEducationBG(EmployeeEducationBGEntity employeeEducationBGEntity,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeEducationBGEntity> request = new HttpEntity<>(employeeEducationBGEntity,headers);
			System.out.println(request);
			ResponseEntity<EmployeeEducationBGEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEducation/addEmployeeEducation", 
					  HttpMethod.POST, 
					  request, 
					  EmployeeEducationBGEntity.class);	
			return response;

			}
		}catch (HttpClientErrorException e) {
			StringBuilder builder = new StringBuilder();
			builder.append("Message="+e.getMessage());
			builder.append(",Status code="+e.getStatusCode());
			builder.append(",Response="+e.getResponseBodyAsString());
			System.out.print("EmployeeInfoService >> Exception - ["+builder.toString()+"]");
		}catch (Exception e) {
			StringBuilder builder = new StringBuilder();
			builder.append("Message="+e.getMessage());
			builder.append(",Response="+e.toString());
			System.out.print("EmployeeInfoService >> Exception - ["+builder.toString()+"]");
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeEducationBGEntityV2[]> GetEmployeeEducationBG(String authToken, String empID){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeEducationBGEntityV2> request = new HttpEntity<EmployeeEducationBGEntityV2>(headers);	  		
			ResponseEntity<EmployeeEducationBGEntityV2[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEducation/getEmployeeEducationByEmpId/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  EmployeeEducationBGEntityV2[].class);
			if(response.getStatusCodeValue() == 404) {
				return null;
			}
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmploymentHistoryEntity> addEmploymentHistory(EmploymentHistoryEntity employmentHistoryEntity,String authToken) {		
		try {			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmploymentHistoryEntity> request = new HttpEntity<>(employmentHistoryEntity,headers);
			ResponseEntity<EmploymentHistoryEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEmploymentHistory/addEmployeeEmploymentHistory", 
					  HttpMethod.POST, 
					  request, 
					  EmploymentHistoryEntity.class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmploymentHistoryByEmpId[]> GetEmploymentHistoryEmpID(String authToken, String empID){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmploymentHistoryByEmpId> request = new HttpEntity<EmploymentHistoryByEmpId>(headers);	  		
			ResponseEntity<EmploymentHistoryByEmpId[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEmploymentHistory/getEmployeeEmploymentHistoryByEmpId/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  EmploymentHistoryByEmpId[].class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	@Override
	public ResponseEntity<RefereeEntity> addReferee(RefereeEntity refereeEntity,String authToken) {		
		try {			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<RefereeEntity> request = new HttpEntity<>(refereeEntity,headers);
			ResponseEntity<RefereeEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeReferee/addEmployeeReferee", 
					  HttpMethod.POST, 
					  request, 
					  RefereeEntity.class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<RefereeEntity[]> GetRefereeEntityEmpID(String authToken, String empID){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<RefereeEntity> request = new HttpEntity<RefereeEntity>(headers);	  		
			ResponseEntity<RefereeEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeReferee/getEmployeeRefereeByEmpId/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  RefereeEntity[].class);		
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	@Override
	public ResponseEntity<EmployeeEducationResponse> GetEmployeeEducationBGByID(int id,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeEducationResponse> request = new HttpEntity<EmployeeEducationResponse>(headers);
			
				ResponseEntity<EmployeeEducationResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEducation/getEmployeeEducationById/" +id, 
				//ResponseEntity<EmployeeEducationResponse> response = restTemplate.exchange("http://10.100.112.86:9191/v1/employeeEducation/getEmployeeEducationById/" +id, 
					  HttpMethod.GET, 
					  request, 
					  EmployeeEducationResponse.class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeProffesionalCertification> addEmployeeProffesionalCertification(EmployeeProffesionalCertification employeeProffesionalCertification,String authToken) {		
		try {			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeProffesionalCertification> request = new HttpEntity<>(employeeProffesionalCertification,headers);
			ResponseEntity<EmployeeProffesionalCertification> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeCertification/addEmployeeCertification", 
					  HttpMethod.POST, 
					  request, 
					  EmployeeProffesionalCertification.class);		
			System.out.println(response);
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeProfessionalCertificationResponse[]> GetEmployeeProffesionalCertification(String authToken, String empID){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeProfessionalCertificationResponse> request = new HttpEntity<EmployeeProfessionalCertificationResponse>(headers);	  		
			ResponseEntity<EmployeeProfessionalCertificationResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeCertification/getEmployeeCertification/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  EmployeeProfessionalCertificationResponse[].class);		
			return response;
		}
		}catch (HttpClientErrorException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeRelatives> addEmployeeRelatives(EmployeeRelatives employeeRelatives,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeRelatives> request = new HttpEntity<>(employeeRelatives,headers);
			System.out.println(request);
			ResponseEntity<EmployeeRelatives> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeRelative/addEmployeeRelative", 
					  HttpMethod.POST, 
					  request, 
					  EmployeeRelatives.class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeRelativeResponse[]> GetEmployeeRelatives(String authToken, String empID){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeRelativeResponse> request = new HttpEntity<EmployeeRelativeResponse>(headers);	  		
			ResponseEntity<EmployeeRelativeResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeRelative/getEmployeeRelativeByEmpId/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  EmployeeRelativeResponse[].class);		
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	@Override
	public ResponseEntity<EmployeeBirthDetailsRequest> addEmployeeBirthDetails(EmployeeBirthDetailsRequest EmployeeBirthDetailsRequest,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeBirthDetailsRequest> request = new HttpEntity<>(EmployeeBirthDetailsRequest,headers);
			System.out.println(request);
			ResponseEntity<EmployeeBirthDetailsRequest> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeBirthDetails/addEmpBirthDetails", 
					  HttpMethod.POST, 
					  request, 
					  EmployeeBirthDetailsRequest.class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeBirthDetailsResponse> GetEmployeeBirthDetails(String authToken, String empID){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeBirthDetailsResponse> request = new HttpEntity<EmployeeBirthDetailsResponse>(headers);	  		
			ResponseEntity<EmployeeBirthDetailsResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeBirthDetails/getEmployeeBirthDetailsByEmpId/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  EmployeeBirthDetailsResponse.class);		
			System.out.println(response.getBody());
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeAddressContactRequest> addEmployeeAddressContact(EmployeeAddressContactRequest employeeAddressContactRequest,String authToken) {		
		
		StringBuilder builder = new StringBuilder();
		builder.append("CityID="+employeeAddressContactRequest.getAdresscityid());
		builder.append(",AddressID="+employeeAddressContactRequest.getAdressid());
		builder.append(",ContactID="+employeeAddressContactRequest.getContactid());
		System.out.print("EmployeeInfoService >> Create - ["+builder.toString()+"]");
		
		HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
		// build the request
		HttpEntity<EmployeeAddressContactRequest> request = new HttpEntity<>(employeeAddressContactRequest,headers);
		ResponseEntity<EmployeeAddressContactRequest> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeAddress/addEmployeeAddress", 
				  HttpMethod.POST, 
				  request, 
				  EmployeeAddressContactRequest.class);	
		return response;
	}

	@Override
	public ResponseEntity<EmploymentHistoryEntityById> GetEmploymentHistoryById(int id,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmploymentHistoryEntityById> request = new HttpEntity<EmploymentHistoryEntityById>(headers);
			
				ResponseEntity<EmploymentHistoryEntityById> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEmploymentHistory/getEmployeeEmploymentHistoryById/" +id, 
					  HttpMethod.GET, 
					  request, 
					  EmploymentHistoryEntityById.class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<RefereeEntity> GetRefereeEntityByID(int id,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<RefereeEntity> request = new HttpEntity<RefereeEntity>(headers);
			
				ResponseEntity<RefereeEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeReferee/getEmployeeRefereeById/" +id, 
					  HttpMethod.GET, 
					  request, 
					  RefereeEntity.class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmployeeRelativeResponse> GeEmployeeRelativeByID(int id,String authToken) {		
		
		try {		
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeRelativeResponse> request = new HttpEntity<EmployeeRelativeResponse>(headers);			
				ResponseEntity<EmployeeRelativeResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeRelative/getEmployeeRelativeById/" +id, 
					  HttpMethod.GET, 
					  request, 
					  EmployeeRelativeResponse.class);				
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmployeeProfessionalCertificationResponse> GetEmployeeProfessionalCertificationByID(int id,String authToken) {		
		
		try {		
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeProfessionalCertificationResponse> request = new HttpEntity<EmployeeProfessionalCertificationResponse>(headers);			
				ResponseEntity<EmployeeProfessionalCertificationResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeCertification/getEmployeeCertificationById/" +id, 
					  HttpMethod.GET, 
					  request, 
					  EmployeeProfessionalCertificationResponse.class);				
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeAddressContactResponse[]> GetEmployeeAddressContact(String authToken, String empID){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeAddressContactResponse[]> request = new HttpEntity<EmployeeAddressContactResponse[]>(headers);	  		
			ResponseEntity<EmployeeAddressContactResponse[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeAddress/getEmployeeAddressByEmpId/" + Long.parseLong(empID), 
					  HttpMethod.GET, 
					  request, 
					  EmployeeAddressContactResponse[].class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeAddressContactResponse> GetEmployeeAddressContactByID(int addressID, int contactID,  String authToken) {		
		
		try {		
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeAddressContactResponse> request = new HttpEntity<EmployeeAddressContactResponse>(headers);			
				ResponseEntity<EmployeeAddressContactResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeAddress/getEmployeeAddressByAdressIdAndContactId/" +addressID + "/" +contactID, 
					  HttpMethod.GET, 
					  request, 
					  EmployeeAddressContactResponse.class);				
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeBirthDetailsResponse> GetEmployeeBirthDetailsByID(int id,String authToken) {		
		
		try {		
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeBirthDetailsResponse> request = new HttpEntity<EmployeeBirthDetailsResponse>(headers);			
				ResponseEntity<EmployeeBirthDetailsResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeBirthDetails/getEmployeeBirthDetailsById/" +id, 
					  HttpMethod.GET, 
					  request, 
					  EmployeeBirthDetailsResponse.class);				
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public ResponseEntity<EmployeeEducationBGEntity> deleteEmploymentEducation(int id, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeEducationBGEntity> request = new HttpEntity<EmployeeEducationBGEntity>(headers);
			System.out.println(request);
			ResponseEntity<EmployeeEducationBGEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEducation/deleteEmployeeEducation/"+ id, 
					  HttpMethod.DELETE, 
					  request, 
					  EmployeeEducationBGEntity.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeEducationBGEntity> editEmployeeEducationBG(int id, EmployeeEducationBGEntity employeeEducationBGEntity,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeEducationBGEntity> request = new HttpEntity<>(employeeEducationBGEntity,headers);
			ResponseEntity<EmployeeEducationBGEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEducation/updateEmployeeEducation/" + id, 
					  HttpMethod.PUT, 
					  request, 
					  EmployeeEducationBGEntity.class);	
			System.out.println(response);
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmploymentHistoryEntity> deleteEmploymentHistory(int id, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmploymentHistoryEntity> request = new HttpEntity<EmploymentHistoryEntity>(headers);
			System.out.println(request);
			ResponseEntity<EmploymentHistoryEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEmploymentHistory/deleteEmployeeEmploymentHistory/"+ id, 
					  HttpMethod.DELETE, 
					  request, 
					  EmploymentHistoryEntity.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmploymentHistoryEntity> editEmploymentHistory(int id, EmploymentHistoryEntity employmentHistoryE,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmploymentHistoryEntity> request = new HttpEntity<>(employmentHistoryE,headers);
			ResponseEntity<EmploymentHistoryEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeEmploymentHistory/updateEmployeeEmploymentHistory/" + id, 
					  HttpMethod.PUT, 
					  request, 
					  EmploymentHistoryEntity.class);	
			System.out.println(response);
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<RefereeEntity> deleteReferee(int id, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<RefereeEntity> request = new HttpEntity<RefereeEntity>(headers);
			ResponseEntity<RefereeEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeReferee/deleteEmployeeReferee/"+ id, 
					  HttpMethod.DELETE, 
					  request, 
					  RefereeEntity.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<RefereeEntity> editRefereeEntity(int id, RefereeEntity refereeEntity,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<RefereeEntity> request = new HttpEntity<>(refereeEntity,headers);
			ResponseEntity<RefereeEntity> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeReferee/updateEmployeeReferee/" + id, 
					  HttpMethod.PUT, 
					  request, 
					  RefereeEntity.class);	
			System.out.println(response);
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeProfessionalCertificationResponse> deleteProffesionalCertification(int id, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeProfessionalCertificationResponse> request = new HttpEntity<EmployeeProfessionalCertificationResponse>(headers);
			ResponseEntity<EmployeeProfessionalCertificationResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeCertification/deleteEmployeeCertification/"+ id, 
					  HttpMethod.DELETE, 
					  request, 
					  EmployeeProfessionalCertificationResponse.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmployeeProfessionalCertificationResponse> editProffesionalCertification(int id, EmployeeProfessionalCertificationResponse employeeProffesionalCertification,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeProfessionalCertificationResponse> request = new HttpEntity<>(employeeProffesionalCertification,headers);
			ResponseEntity<EmployeeProfessionalCertificationResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeCertification/updateEmployeeCertification/" + id, 
					  HttpMethod.PUT, 
					  request, 
					  EmployeeProfessionalCertificationResponse.class);	
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmployeeBirthDetailsRequest> deleteEmployeeBirthDetails(int id, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeBirthDetailsRequest> request = new HttpEntity<EmployeeBirthDetailsRequest>(headers);
			ResponseEntity<EmployeeBirthDetailsRequest> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeBirthDetails/deleteEmployeeBirthDetails/"+ id, 
					  HttpMethod.DELETE, 
					  request, 
					  EmployeeBirthDetailsRequest.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmployeeBirthDetailsRequest> editEmployeeBirthDetails(int id, EmployeeBirthDetailsRequest employeeBirthDetailsRequest,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeBirthDetailsRequest> request = new HttpEntity<>(employeeBirthDetailsRequest,headers);
			ResponseEntity<EmployeeBirthDetailsRequest> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeBirthDetails/updateEmployeeBirthDetails/" + id, 
					  HttpMethod.PUT, 
					  request, 
					  EmployeeBirthDetailsRequest.class);	
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<EmployeeAddressContactResponse> deleteEmployeeAddressContact(int addressID, int contactID, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeAddressContactResponse> request = new HttpEntity<EmployeeAddressContactResponse>(headers);
			ResponseEntity<EmployeeAddressContactResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeAddress/deleteEmployeeAddress/"+ addressID + "/" + contactID, 
					  HttpMethod.DELETE, 
					  request, 
					  EmployeeAddressContactResponse.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeAddressContactRequest>  editEmployeeAddressContact(int addressID, int contactID, EmployeeAddressContactRequest employeeAddressContactRequest, String authToken){
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("CityID="+employeeAddressContactRequest.getAdresscityid());
			builder.append(",AddressID="+employeeAddressContactRequest.getAdressid());
			builder.append(",ContactID="+employeeAddressContactRequest.getContactid());
			System.out.print("EmployeeInfoService >> Edit - ["+builder.toString()+"]");
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeAddressContactRequest> request = new HttpEntity<>(employeeAddressContactRequest,headers);
			ResponseEntity<EmployeeAddressContactRequest> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeAddress/updateEmployeeAddress/" +addressID + "/" + contactID, 
					  HttpMethod.PUT, 
					  request, 
					  EmployeeAddressContactRequest.class);	
			return response;

		}
		}catch (HttpClientErrorException e) {
			StringBuilder builder = new StringBuilder();
			builder.append("Message="+e.getMessage());
			builder.append(",Status code="+e.getStatusCode());
			builder.append(",Response="+e.getResponseBodyAsString());
			System.out.print("EmployeeInfoService >> Exception - ["+builder.toString()+"]");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeRelativeResponse> deleteEmployeeRelatives(int id, String authToken){		
		try {
			
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeRelativeResponse> request = new HttpEntity<EmployeeRelativeResponse>(headers);
			ResponseEntity<EmployeeRelativeResponse> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeRelative/deleteEmployeeRelative/"+ id, 
					  HttpMethod.DELETE, 
					  request, 
					  EmployeeRelativeResponse.class);	
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	@Override
	public ResponseEntity<EmployeeRelatives> editEmployeeRelatives(int id, EmployeeRelatives employeeRelative,String authToken) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeRelatives> request = new HttpEntity<>(employeeRelative,headers);
			ResponseEntity<EmployeeRelatives> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeRelative/updateEmployeeRelative/" + id, 
					  HttpMethod.PUT, 
					  request, 
					  EmployeeRelatives.class);	
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object addEmployeeContactAddress(EmployeeAddressContactRequest employeeAddressContactRequest, String authToken) {
		try {			
			if(authToken != null) {		
				HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
				// build the request
				HttpEntity<EmployeeAddressContactRequest> request = new HttpEntity<>(employeeAddressContactRequest,headers);
				ResponseEntity<EmployeeAddressContactRequest> response = restTemplate.exchange(AppConstants.BASE_URL+"/employeeAddress/addEmployeeAddress", 
						  HttpMethod.POST, 
						  request, 
						  EmployeeAddressContactRequest.class);	
				if(response==null) {
					System.out.println("Employee Info Services >> Response is NULL");
					return "Employee Info Services >> Response is NULL";
				}
				int statusCode = response.getStatusCodeValue();
				System.out.println("Employee Info Services >> Status Code is "+statusCode);
				System.out.println("Response >> "+response);
				if(statusCode!=200) {
					return "Employee Info Services >> Status Code is "+statusCode;
				}
				return response;
			}
			}catch (HttpClientErrorException e) {
				e.printStackTrace();
				StringBuilder exceptionBuilder = new StringBuilder();
				exceptionBuilder.append("HttpClientErrorException - "+e.toString());
				exceptionBuilder.append("\nMessage - "+e.getMessage());
				exceptionBuilder.append("\nRaw status code - "+e.getRawStatusCode());
				exceptionBuilder.append("\nStatus text - "+e.getStatusText());
				exceptionBuilder.append("\nStack trace - "+e.getStackTrace().toString());
				return exceptionBuilder.toString();
			}catch (Exception e) {
				e.printStackTrace();
				StringBuilder exceptionBuilder = new StringBuilder();
				exceptionBuilder.append("Exception - "+e.toString());
				exceptionBuilder.append("\nMessage - "+e.getMessage());
				exceptionBuilder.append("\nStack trace - "+e.getStackTrace().toString());
				return exceptionBuilder.toString();
			}
			return "Authorization Token not valid";
	}
	
	
	@Override
	public ResponseEntity<EmployeeEntity> uploadEmployeePassportSize(String authToken, EmployeeEntity employeeEntity) {		
		try {
			
		if(authToken != null) {		
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		
			// build the request
			HttpEntity<EmployeeEntity> request = new HttpEntity<>(employeeEntity, headers);
			
			ResponseEntity<EmployeeEntity> response = restTemplate.exchange(AppConstants.BASE_URL
					+"/hrmsEmployee/updateEmployeePhoto", 
					  HttpMethod.PUT, 
					  request, 
					  EmployeeEntity.class);	
			System.out.println(response);
			return response;

		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
}

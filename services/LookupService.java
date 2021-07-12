package com.tcra.hrms.services;

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
import com.tcra.hrms.entity.AttachmentTypeEntity;
import com.tcra.hrms.entity.CertificationCategoryEntity;
import com.tcra.hrms.entity.CityEntity;
import com.tcra.hrms.entity.ContactTypeEntity;
import com.tcra.hrms.entity.CountryEntity;
import com.tcra.hrms.entity.EducationCourseEntity;
import com.tcra.hrms.entity.EducationInstitutionEntity;
import com.tcra.hrms.entity.EducationLevelEntity;
import com.tcra.hrms.entity.EmployeeRelativeCategoryEntity;
import com.tcra.hrms.entity.LocationAddressEntity;
import com.tcra.hrms.entity.NationalityEntity;
import com.tcra.hrms.entity.RefereeCategoryEntity;
import com.tcra.hrms.entity.YearEntity;

@Component
public class LookupService implements ILookupService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate; //restTemplate
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public ResponseEntity<EducationLevelEntity[]> GetEducationLevel(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EducationLevelEntity> request = new HttpEntity<EducationLevelEntity>(headers);	  		
			ResponseEntity<EducationLevelEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationlevel/getAllEducationlevel", 
					  HttpMethod.GET, 
					  request, 
					  EducationLevelEntity[].class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<EducationCourseEntity[]> GetEducationCourses(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EducationCourseEntity> request = new HttpEntity<EducationCourseEntity>(headers);	  		
			ResponseEntity<EducationCourseEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/getAllEducationcourse", 
					  HttpMethod.GET, 
					  request, 
					  EducationCourseEntity[].class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EducationCourseEntity[]> GetEducationCourses(int id, String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EducationCourseEntity> request = new HttpEntity<EducationCourseEntity>(headers);	  		
			ResponseEntity<EducationCourseEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educationcourse/getEducationcourseByLevelCode/" + id, 
					  HttpMethod.GET, 
					  request, 
					  EducationCourseEntity[].class);	
		
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EducationInstitutionEntity[]> GetEducationInstitution(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EducationInstitutionEntity> request = new HttpEntity<EducationInstitutionEntity>(headers);	  		
			ResponseEntity<EducationInstitutionEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/educatinstitution/getAllEducationinstitution", 
					  HttpMethod.GET, 
					  request, 
					  EducationInstitutionEntity[].class);			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<YearEntity[]> GetYear(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<YearEntity> request = new HttpEntity<YearEntity>(headers);	  		
			ResponseEntity<YearEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/lookupyear/getAllYear", 
					  HttpMethod.GET, 
					  request, 
					  YearEntity[].class);
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<AttachmentTypeEntity[]> GetAttachmentType(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<AttachmentTypeEntity> request = new HttpEntity<AttachmentTypeEntity>(headers);	  		
			ResponseEntity<AttachmentTypeEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/attachmentType/getAllAttachmentType", 
					  HttpMethod.GET, 
					  request, 
					  AttachmentTypeEntity[].class);
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<CountryEntity[]> GetCountry(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<CountryEntity> request = new HttpEntity<CountryEntity>(headers);	  		
			ResponseEntity<CountryEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationCountry/getAllLocationCountry", 
					  HttpMethod.GET, 
					  request, 
					  CountryEntity[].class);
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<CityEntity[]> GetCityEntity(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<CityEntity> request = new HttpEntity<CityEntity>(headers);	  		
			ResponseEntity<CityEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationCity/getAllLocationCity", 
					  HttpMethod.GET, 
					  request, 
					  CityEntity[].class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<LocationAddressEntity[]> GetLocationAddressEntity(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<LocationAddressEntity> request = new HttpEntity<LocationAddressEntity>(headers);	  		
			ResponseEntity<LocationAddressEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/locationAddressType/getAllLocationAddressType", 
					  HttpMethod.GET, 
					  request, 
					  LocationAddressEntity[].class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<ContactTypeEntity[]> GetContactTypeEntity(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<ContactTypeEntity> request = new HttpEntity<ContactTypeEntity>(headers);	  		
			ResponseEntity<ContactTypeEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/contactType/listAllContactType", 
					  HttpMethod.GET, 
					  request, 
					  ContactTypeEntity[].class);		
			
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<NationalityEntity[]> GetNationality(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<NationalityEntity> request = new HttpEntity<NationalityEntity>(headers);	  		
			ResponseEntity<NationalityEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/nationality/getAllNationality", 
					  HttpMethod.GET, 
					  request, 
					  NationalityEntity[].class);
			System.out.println(response);
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<RefereeCategoryEntity[]> GetRefereeCategory(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<RefereeCategoryEntity> request = new HttpEntity<RefereeCategoryEntity>(headers);	  		
			ResponseEntity<RefereeCategoryEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/refereeCategory/getAllRefereeCategory", 
					  HttpMethod.GET, 
					  request, 
					  RefereeCategoryEntity[].class);		
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<CertificationCategoryEntity[]> GetCertificationCategory(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<CertificationCategoryEntity> request = new HttpEntity<CertificationCategoryEntity>(headers);	  		
			ResponseEntity<CertificationCategoryEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/certificationCategory/getAllCertificationCategory", 
					  HttpMethod.GET, 
					  request, 
					  CertificationCategoryEntity[].class);		
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ResponseEntity<EmployeeRelativeCategoryEntity[]> GetEmployeeRelativeCategoryEntity(String authToken){		
		try {
		if(authToken != null) {
			HttpHeaders headers = HttpHeader.setHttpHeader(authToken);		  
			HttpEntity<EmployeeRelativeCategoryEntity> request = new HttpEntity<EmployeeRelativeCategoryEntity>(headers);	  		
			ResponseEntity<EmployeeRelativeCategoryEntity[]> response = restTemplate.exchange(AppConstants.BASE_URL+"/relativeCategory/getAllRelativeCategory", 
					  HttpMethod.GET, 
					  request, 
					  EmployeeRelativeCategoryEntity[].class);		
			System.out.println(response.getBody());
			return response;
		}
		}catch (HttpClientErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

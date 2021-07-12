package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.EducationInstitution;

@Component
public interface IEducationInstitutionService {
	ResponseEntity<EducationInstitution[]> GetAll(String authToken);
	ResponseEntity<EducationInstitution[]> GetAll(String authToken,int countryId);
	ResponseEntity<EducationInstitution> Get(String authToken,int id);
	ResponseEntity<EducationInstitution> Add(String authToken,EducationInstitution institution);
	ResponseEntity<EducationInstitution> Update(String authToken,int id,EducationInstitution institution);
	ResponseEntity<Integer> Delete(String authToken,int id);
}

package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.EducationInstitutionCategory;
import com.tcra.hrms.dto.EducationInstitutionType;

@Component
public interface IEducationInstitutionCategoryService {
	ResponseEntity<EducationInstitutionCategory[]> GetAll(String authToken);
}

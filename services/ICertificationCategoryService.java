package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.CertificationCategory;

@Component
public interface ICertificationCategoryService {
	ResponseEntity<CertificationCategory[]> GetAll(String authToken);
	ResponseEntity<CertificationCategory> Get(String authToken,int id);
	ResponseEntity<CertificationCategory> Add(String authToken,CertificationCategory category);
	ResponseEntity<CertificationCategory> Update(String authToken,int id,CertificationCategory category);
	ResponseEntity<Integer> Delete(String authToken,int id);
}

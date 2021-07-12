package com.tcra.hrms.services.training;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.entity.training.FinancialYear;

@Component
public interface IFinancialYearServices {
	ResponseEntity<FinancialYear[]> GetAll(String authToken);
}

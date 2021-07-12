package com.tcra.hrms.services.appraisal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.appraisal.FinancialYear;
import com.tcra.hrms.dto.leave.LeaveType;

@Component
public interface IFinancialYearServices {
	ResponseEntity<FinancialYear[]> GetAll(String authToken);
}

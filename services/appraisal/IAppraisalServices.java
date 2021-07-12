package com.tcra.hrms.services.appraisal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.appraisal.EmployeePerformance;
import com.tcra.hrms.dto.appraisal.PerformanceBehaviour;
import com.tcra.hrms.dto.appraisal.PerformancePlanning;
import com.tcra.hrms.dto.payroll.Payslip;

@Component
public interface IAppraisalServices {
	public ResponseEntity<PerformancePlanning> CreateAgreement(String authToken,PerformancePlanning planning);
	public ResponseEntity<?> GetEmployeePerformance(String authToken,int employeeId,int year);
	public ResponseEntity<?> GetEmployeeMidYearReview(String authToken, int eppaid);
	public ResponseEntity<?> GetEmployeeAnnualYearReview(String authToken, int eppaid);
	public ResponseEntity<?> GetEmployeeBehaviour(String authToken, int eppaid);
	public ResponseEntity<PerformanceBehaviour> UpdateBehaviour(String authToken,int id,
			PerformanceBehaviour behaviour);
	public ResponseEntity<PerformanceBehaviour> CreateBehaviour(String authToken,
			PerformanceBehaviour behaviour);
}

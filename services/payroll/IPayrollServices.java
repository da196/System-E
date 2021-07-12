package com.tcra.hrms.services.payroll;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.payroll.Payslip;

@Component
public interface IPayrollServices {
	public ResponseEntity<Payslip> GetPayslip(String authToken,int year,int month,int employeeId);
}

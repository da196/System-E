package com.tcra.hrms.services.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.configuration.HttpHeader;
import com.tcra.hrms.dto.payroll.Payslip;

@Service
public class PayrollServices implements IPayrollServices {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<Payslip> GetPayslip(String authToken, int year, int month,int employeeId) {
		// headers
		final HttpHeaders headers = HttpHeader.setHttpHeader(authToken);
		final HttpEntity<Payslip> request = new HttpEntity<Payslip>(headers);
		final ResponseEntity<Payslip> response = restTemplate.exchange(
				AppConstants.BASE_URL + "/payrollReports/getPayrollPaySlip/" + employeeId+"/"+year+"/"+month,
				HttpMethod.GET, request, Payslip.class);
		return response;
	}

}

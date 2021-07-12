package com.tcra.hrms.controller.payroll;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.core.HttpStatusCodes;
import com.tcra.hrms.dto.leave.EmployeeLeaveBalance;
import com.tcra.hrms.dto.payroll.PayrollDate;
import com.tcra.hrms.dto.payroll.Payslip;
import com.tcra.hrms.dto.payroll.PayslipRun;
import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;
import com.tcra.hrms.services.payroll.IPayrollServices;
import com.tcra.hrms.utils.DateUtils;

@Controller
public class PayrollController {
	private static final Logger logger = Logger.getLogger(PayrollController.class); // log4j

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private IPayrollServices payrollServices;

	@RequestMapping(value = "/payroll-payslip.htm", method = RequestMethod.GET)
	public ModelAndView SendPayslip() {
		// initialize
		ModelAndView mv = new ModelAndView("payroll/payroll-payslip");

		return mv;
	}
	
	@RequestMapping(value = "/v1/payroll-payslip", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetPayslip(@Validated @RequestBody PayslipRun payslipRun) {
		logger.info(String.format("Payslip run data passed is {%s} ", payslipRun));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user =  authenticationFacade.getUser();
			if(user==null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final int employeeId = user.getEmpId();
			final PayrollDate payrollDate = DateUtils.toPayrollDate(payslipRun.getPayrolldate());
			if(payrollDate==null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			final int year = payrollDate.getYear();
			final int month = payrollDate.getMonth();
			final ResponseEntity<Payslip> response = payrollServices.GetPayslip(authToken, year, month, employeeId );
			
			int code = response.getStatusCodeValue();
			logger.info(String.format("Get payslip status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Payslip>(response.getBody(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

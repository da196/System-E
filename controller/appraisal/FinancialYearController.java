package com.tcra.hrms.controller.appraisal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.core.HttpStatusCodes;
import com.tcra.hrms.dto.appraisal.FinancialYear;
import com.tcra.hrms.dto.leave.Leave;
import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;
import com.tcra.hrms.services.appraisal.IFinancialYearServices;

@Controller
public class FinancialYearController {
	private static final Logger logger = Logger.getLogger(FinancialYearController.class); // log4j

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private IFinancialYearServices financialYearServices;

	@RequestMapping(value = "/v1/financial-year-all", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public  @ResponseBody ResponseEntity<?> GetAll() {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final Authentication auth = authenticationFacade.getAuthentication();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<FinancialYear[]> response = financialYearServices.GetAll(authToken);
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("FinancialYear status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			} 
			else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<FinancialYear[]>(response.getBody(), HttpStatus.OK);
			} 
			else {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

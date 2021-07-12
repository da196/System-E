package com.tcra.hrms.controller.appraisal;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.core.HttpStatusCodes;
import com.tcra.hrms.dto.appraisal.EmployeePerformance;
import com.tcra.hrms.dto.appraisal.PerformanceBehaviour;
import com.tcra.hrms.dto.appraisal.PerformancePlanning;
import com.tcra.hrms.dto.leave.Leave;
import com.tcra.hrms.dto.payroll.Payslip;
import com.tcra.hrms.dto.payroll.PayslipRun;
import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;
import com.tcra.hrms.services.appraisal.IAppraisalServices;

@Controller
public class AppraisalController {
	private static final Logger logger = Logger.getLogger(AppraisalController.class); // log4j

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private IAppraisalServices appraisalServices;

	@RequestMapping(value = "/add-appraisal.htm", method = RequestMethod.GET)
	public ModelAndView AddAppraisal(Model model) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final Authentication auth = authenticationFacade.getAuthentication();

		// mv
		ModelAndView mv = new ModelAndView("appraisal/appraisal-add");
		return mv;
	}
	
	@RequestMapping(value = "/my-appraisal.htm", method = RequestMethod.GET)
	public ModelAndView MyAppraisal(Model model) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final Authentication auth = authenticationFacade.getAuthentication();
		final int employeeId = user.getEmpId();

		// mv
		ModelAndView mv = new ModelAndView("appraisal/my-appraisal");
		mv.addObject("employeeid", employeeId);
		return mv;
	}

	// --------------------------------------------------------------------------------
	// - ajax end point
	// --------------------------------------------------------------------------------
	@RequestMapping(value = "/v1/appraisal-performance-behaviour-update/{id}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> UpdateBehaviour(@PathVariable("id")int id,@Validated @RequestBody PerformanceBehaviour behaviour) {
		logger.info(String.format("Planning behaviour data passed is {%s} ", behaviour));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final int eabId = id;
			if (eabId == 0) {
				return new ResponseEntity<>("Eab id is required",HttpStatus.BAD_REQUEST);
			}
			final int employeeId = user.getEmpId();
			PerformanceBehaviour performanceBehaviour = behaviour;
			// set employee id
			performanceBehaviour.setEmployeeid(employeeId);
			logger.info(String.format("Planning behaviour data updated is {%s} ", performanceBehaviour));

			final ResponseEntity<PerformanceBehaviour> response = appraisalServices.UpdateBehaviour(authToken,
					eabId,performanceBehaviour);
			int code = response.getStatusCodeValue();
			logger.info(String.format("Update peformance behaviour status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<PerformanceBehaviour>(response.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/appraisal-performance-behaviour-add", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> CreateBehaviour(@Validated @RequestBody PerformanceBehaviour behaviour) {
		logger.info(String.format("Planning behaviour data passed is {%s} ", behaviour));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final int employeeId = user.getEmpId();
			PerformanceBehaviour performanceBehaviour = behaviour;
			// set employee id
			performanceBehaviour.setEmployeeid(employeeId);
			logger.info(String.format("Planning behaviour data updated is {%s} ", performanceBehaviour));

			final ResponseEntity<PerformanceBehaviour> response = appraisalServices.CreateBehaviour(authToken,
					performanceBehaviour);
			int code = response.getStatusCodeValue();
			logger.info(String.format("Create peformance behaviour status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<PerformanceBehaviour>(response.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/appraisal-behaviour/{eppaid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeBehaviour(@PathVariable("eppaid")int eppaid) {
		logger.info(String.format("Employee performance id data passed is {%s} ", eppaid));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final ResponseEntity<?> response = appraisalServices.GetEmployeeBehaviour(authToken,eppaid);
			if(response==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			int code = response.getStatusCodeValue();
			logger.info(String.format("Get employee performance status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Object>(response.getBody(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/appraisal-annual-year-review/{eppaid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeAnnualYearReview(@PathVariable("eppaid")int eppaid) {
		logger.info(String.format("Employee performance id data passed is {%s} ", eppaid));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final ResponseEntity<?> response = appraisalServices.GetEmployeeAnnualYearReview(authToken,eppaid);
			if(response==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			int code = response.getStatusCodeValue();
			logger.info(String.format("Get employee performance status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Object>(response.getBody(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/appraisal-mid-year-review/{eppaid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeMidYearReview(@PathVariable("eppaid")int eppaid) {
		logger.info(String.format("Employee performance id data passed is {%s} ", eppaid));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final ResponseEntity<?> response = appraisalServices.GetEmployeeMidYearReview(authToken,eppaid);
			if(response==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			int code = response.getStatusCodeValue();
			logger.info(String.format("Get employee performance status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Object>(response.getBody(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/appraisal-employee-performance/{employeeid}/{year}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeePerformance(@PathVariable("employeeid")int employeeid,@PathVariable("year")int year) {
		logger.info(String.format("Employee performance data passed is {%s} ", employeeid));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final ResponseEntity<?> response = appraisalServices.GetEmployeePerformance(authToken,
					employeeid,year);
			if(response==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			int code = response.getStatusCodeValue();
			logger.info(String.format("Get employee performance status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Object>(response.getBody(),HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/appraisal-agreement-add", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> CreateAgreement(@Validated @RequestBody PerformancePlanning planning) {
		logger.info(String.format("Planning and agreement data passed is {%s} ", planning));
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			final int employeeId = user.getEmpId();
			PerformancePlanning performancePlanning = planning;
			// set employee id
			if (performancePlanning.getPerformanceEppa() != null) {
				performancePlanning.getPerformanceEppa().setEmployeeid(employeeId);
			}
			logger.info(String.format("Planning and agreement data updated is {%s} ", performancePlanning));

			final ResponseEntity<PerformancePlanning> response = appraisalServices.CreateAgreement(authToken,
					performancePlanning);
			int code = response.getStatusCodeValue();
			logger.info(String.format("Create agreement status code {%d}", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<PerformancePlanning>(response.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

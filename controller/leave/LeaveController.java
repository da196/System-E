package com.tcra.hrms.controller.leave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.controller.EducationCourseController;
import com.tcra.hrms.core.HttpStatusCodes;
import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.EducationCourse;
import com.tcra.hrms.dto.Employee;
import com.tcra.hrms.dto.leave.EmployeeLeaveBalance;
import com.tcra.hrms.dto.leave.Leave;
import com.tcra.hrms.dto.leave.LeaveApprovalStatus;
import com.tcra.hrms.dto.leave.LeaveCommutation;
import com.tcra.hrms.dto.leave.LeaveType;
import com.tcra.hrms.dto.leave.Recall;
import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;
import com.tcra.hrms.services.IEmployeeServices;
import com.tcra.hrms.services.leave.ILeaveBalanceServices;
import com.tcra.hrms.services.leave.ILeaveCommutationServices;
import com.tcra.hrms.services.leave.ILeaveRecallServices;
import com.tcra.hrms.services.leave.ILeaveServices;
import com.tcra.hrms.services.leave.ILeaveTypeServices;
import com.tcra.hrms.utils.DateUtils;
import  com.tcra.hrms.entity.LeaveRecall;
/*
 * retrieve-roles
 * https://www.baeldung.com/spring-security-check-user-role#:~:text=The%20first%20way%20to%20check,first%20enable%20global%20method%20security.
 * */
@Controller
public class LeaveController {
	private static final Logger logger = Logger.getLogger(LeaveController.class); // log4j

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private IEmployeeServices employeeServices;
	@Autowired
	private ILeaveTypeServices leaveTypeServices;
	@Autowired
	private ILeaveServices leaveServices;
	@Autowired
	private ILeaveBalanceServices leaveBalanceServices;
	@Autowired
	private ILeaveCommutationServices leaveCommutationServices;
	@Autowired
	private ILeaveRecallServices leaveRecallServices;

	@RequestMapping(value = "/leave-apply.htm", method = RequestMethod.GET)
	public ModelAndView ApplyLeave(Model model) {
		model.addAttribute("leave", new Leave());
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final Authentication auth = authenticationFacade.getAuthentication();
		final String authToken  = authenticationFacade.getAuthenticationToken();
		// employees
		ArrayList<Employee> _employeesActing = null;
		EmployeeLeaveBalance _leaveBalances = null;
		final int employeeID = user.getEmpId();
		if (employeeServices != null) {
		
			final ResponseEntity<Employee[]> employees = employeeServices.GetAllLess();
			if (employees != null && employees.getBody() != null) {
				_employeesActing = new ArrayList<Employee>(Arrays.asList(employees.getBody()));
				_employeesActing.removeIf(it -> it.getId() == employeeID);
			}
		}
		List<Employee> _employees = null;
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"))) {
			if (employeeServices != null) {
				final ResponseEntity<Employee[]> employees = employeeServices.GetAllLess();
				if (employees != null && employees.getBody() != null) {
					_employees = Arrays.asList(employees.getBody());
				}
			}
		} else {
			if (employeeServices != null) {
			
				final ResponseEntity<Employee> employee = employeeServices.Get(employeeID);
				if (employee != null && employee.getBody() != null) {
					_employees = new ArrayList<Employee>();
					_employees.add(employee.getBody());
				}
			}
		}
		// leave types
		ArrayList<LeaveType> _leaveTypes = null;
		if (leaveTypeServices != null) {
			final ResponseEntity<LeaveType[]> leaveTypes = leaveTypeServices.GetAll();
			if (auth != null
					&& auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"))) {
				if (leaveTypes != null && leaveTypes.getBody() != null) {
					_leaveTypes = new ArrayList<LeaveType>(Arrays.asList(leaveTypes.getBody()));
				}
			} else {
				// remove casual leave
				if (leaveTypes != null && leaveTypes.getBody() != null) {
					_leaveTypes = new ArrayList<LeaveType>(Arrays.asList(leaveTypes.getBody()));
					Predicate<? super LeaveType> filter = item -> (item.getCode() == 0);
					boolean removed = _leaveTypes.removeIf(filter);
					if (removed) {
						logger.info("Casual leave removed");
					}
				}
			}
		}
		
		final ResponseEntity<EmployeeLeaveBalance> response = leaveBalanceServices.GetAllByEmployee(authToken,
				employeeID);
		// final ResponseEntity<EmployeeLeaveBalance[]> response =
		// leaveBalanceServices.GetAll(authToken);
		if (response != null && response.getBody() != null) {
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear balance status is {%d}", code));
			_leaveBalances = response.getBody();
		}
		// mv
		ModelAndView mv = new ModelAndView("leave/leave-apply");
		mv.addObject("employees", _employees);
		mv.addObject("leavetypes", _leaveTypes);
		mv.addObject("employeesActing", _employeesActing);
		mv.addObject("leaveBalance", _leaveBalances);
		return mv;
	}

	@RequestMapping(value = "/leave-update.htm/{leaveid}", method = RequestMethod.GET)
	public ModelAndView UpdateLeave(@PathVariable("leaveid") int leaveid, Model model,
			RedirectAttributes redirectAttributes) {
		// mv
		ModelAndView mv = null;
		try {
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final Authentication auth = authenticationFacade.getAuthentication();
			final String authToken = authenticationFacade.getAuthenticationToken();

			model.addAttribute("leave", new Leave());
			if (leaveid <= 0) {
				redirectAttributes.addFlashAttribute("error", "System Error");
				mv = new ModelAndView("error");
				return mv;
			}
			// leave
			Leave _leave = null;
			final ResponseEntity<Leave> leave = leaveServices.Get(authToken, leaveid);
			if (leave == null) {
				redirectAttributes.addFlashAttribute("error", "System Error");
				mv = new ModelAndView("error");
				return mv;
			}
			if (leave.getBody() != null) {
				_leave = leave.getBody();
			}
			// employees
			ArrayList<Employee> _employeesActing = null;
			if (employeeServices != null) {
				final int employeeID = user.getEmpId();
				final ResponseEntity<Employee[]> employees = employeeServices.GetAllLess();
				if (employees != null && employees.getBody() != null) {
					_employeesActing = new ArrayList<Employee>(Arrays.asList(employees.getBody()));
					_employeesActing.removeIf(it -> it.getId() == employeeID);
				}
			}
			List<Employee> _employees = null;
			if (auth != null
					&& auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"))) {
				if (employeeServices != null) {
					final ResponseEntity<Employee[]> employees = employeeServices.GetAllLess();
					if (employees != null && employees.getBody() != null) {
						_employees = Arrays.asList(employees.getBody());
					}
				}
			} else {
				if (employeeServices != null) {
					final int employeeID = user.getEmpId();
					final ResponseEntity<Employee> employee = employeeServices.Get(employeeID);
					if (employee != null && employee.getBody() != null) {
						_employees = new ArrayList<Employee>();
						_employees.add(employee.getBody());
					}
				}
			}
			// leave types
			List<LeaveType> _leaveTypes = null;
			if (leaveTypeServices != null) {
				final ResponseEntity<LeaveType[]> leaveTypes = leaveTypeServices.GetAll();
				if (leaveTypes != null && leaveTypes.getBody() != null) {
					_leaveTypes = Arrays.asList(leaveTypes.getBody());
				}
			}
			mv = new ModelAndView("leave/leave-update");
			mv.addObject("leave", _leave);
			mv.addObject("employees", _employees);
			mv.addObject("leavetypes", _leaveTypes);
			mv.addObject("employeesActing", _employeesActing);
			return mv;
		} catch (Exception ex) {
			ex.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "System Error");
			mv = new ModelAndView("error");
			return mv;
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_SUPERVISOR','ROLE_HEAD_SECTION','ROLE_HEAD_UNIT','ROLE_DIRECTOR','ROLE_DIRECTOR_GENERAL')")
	@RequestMapping(value = "/leave-applications.htm", method = RequestMethod.GET)
	public ModelAndView ManageLeave() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			int supervisorId = user.getEmpId();
			// int supervisorId = 588;
			final ResponseEntity<Leave[]> response = leaveServices.GetAllBySupervisor(authToken, supervisorId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear status is {%d}", code));
				List<Leave> _leaves = null;
				if (response != null && response.getBody() != null) {
					_leaves = Arrays.asList(response.getBody());
				}
				mv = new ModelAndView("leave/leaves");
				mv.addObject("leaves", _leaves);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/my-leave-applications.htm", method = RequestMethod.GET)
	public ModelAndView ManageEmployeeLeave() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();

			final ResponseEntity<Leave[]> response = leaveServices.GetAllEmployee(authToken, employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear status is {%d}", code));
				List<Leave> _leaves = null;
				if (response != null && response.getBody() != null) {
					_leaves = Arrays.asList(response.getBody());
				}
				mv = new ModelAndView("leave/employee-leave");
				mv.addObject("leaves", _leaves);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/my-leave-balance.htm", method = RequestMethod.GET)
	public ModelAndView EmployeeLeaveBalance() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<EmployeeLeaveBalance> response = leaveBalanceServices.GetAllByEmployee(authToken,
					employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear balance status is {%d}", code));
				EmployeeLeaveBalance _leaveBalance = response.getBody();
				mv = new ModelAndView("leave/employee-leave-balance");
				mv.addObject("leaveBalance", _leaveBalance);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/leave-balance.htm", method = RequestMethod.GET)
	public ModelAndView LeaveBalance() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<EmployeeLeaveBalance[]> response = leaveBalanceServices.GetAllBySupervisor(authToken,
					employeeId);
			// final ResponseEntity<EmployeeLeaveBalance[]> response =
			// leaveBalanceServices.GetAll(authToken);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear balance status is {%d}", code));
				EmployeeLeaveBalance[] _leaveBalances = response.getBody();
				mv = new ModelAndView("leave/leave-balance");
				mv.addObject("leaveBalances", _leaveBalances);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/leave-balance-by-supervisor.htm", method = RequestMethod.GET)
	public ModelAndView LeaveBalanceBySupervisor() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<EmployeeLeaveBalance[]> response = leaveBalanceServices.GetAllBySupervisor(authToken,
					employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear balance status is {%d}", code));
				EmployeeLeaveBalance[] _leaveBalances = response.getBody();
				mv = new ModelAndView("leave/leave-balance");
				mv.addObject("leaveBalances", _leaveBalances);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/my-leave-commutation.htm", method = RequestMethod.GET)
	public ModelAndView EmployeeLeaveCommutation() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<LeaveCommutation[]> response = leaveCommutationServices.GetAllByEmployee(authToken,
					employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear commutation status is {%d}", code));
				LeaveCommutation[] _leaveCommutation = response.getBody();
				mv = new ModelAndView("leave/employee-leave-commutation");
				mv.addObject("leaveCommutation", _leaveCommutation);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/leave-commutation.htm", method = RequestMethod.GET)
	public ModelAndView LeaveCommutationBySupervisor() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<LeaveCommutation[]> response = leaveCommutationServices.GetAllBySupervisor(authToken,
					employeeId);
			
			final ResponseEntity<LeaveCommutation[]> responseLeave = leaveCommutationServices.
					GetLeaveCommutationNotApprovedBySupervisorNext(authToken,employeeId);
			
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear balance status is {%d}", code));
				LeaveCommutation[] _leaveCommutation = response.getBody();
				LeaveCommutation[] _leaveCommutationApproval = response.getBody();
//				int approvalCount = _leaveCommutation.length;
				mv = new ModelAndView("leave/leave-commutation");
				
				mv.addObject("approvalCount", _leaveCommutationApproval);
				mv.addObject("leaveCommutation", _leaveCommutation);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/leave-communication-commute.htm", method = RequestMethod.GET)
	public ModelAndView LeaveCommutationCommute(Model model) {
		model.addAttribute("leaveCommute", new LeaveCommutation());
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final Authentication auth = authenticationFacade.getAuthentication();
		// employees
		List<Employee> _employees = null;
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"))) {
			if (employeeServices != null) {
				final ResponseEntity<Employee[]> employees = employeeServices.GetAllLess();
				if (employees != null && employees.getBody() != null) {
					_employees = Arrays.asList(employees.getBody());
				}
			}
		} else {
			if (employeeServices != null) {
				final int employeeID = user.getEmpId();
				final ResponseEntity<Employee> employee = employeeServices.Get(employeeID);
				if (employee != null && employee.getBody() != null) {
					_employees = new ArrayList<Employee>();
					_employees.add(employee.getBody());
				}
			}
		}
		// leave types
		List<LeaveType> _leaveTypes = null;
		if (leaveTypeServices != null) {
			final ResponseEntity<LeaveType[]> leaveTypes = leaveTypeServices.GetAll();
			if (leaveTypes != null && leaveTypes.getBody() != null) {
				_leaveTypes = Arrays.asList(leaveTypes.getBody());
			}
		}
		// mv
		ModelAndView mv = new ModelAndView("leave/leave-commute");
		mv.addObject("employees", _employees);
		mv.addObject("leavetypes", _leaveTypes);
		return mv;
	}



	// --------------------------------------------------------------------------------
	// - ajax end point
	// --------------------------------------------------------------------------------
	@RequestMapping(value = "/v1/leave-apply", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> ApplyLeave(@RequestBody Leave leave) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int employeeId = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("FinancialYear=[" + leave.toString() + "]");
			// parse dates
			// dd-mm-yyyy to yyyy-mm-dd
			try {
				final String dateStart = DateUtils.toYYYYMMDD(leave.getStartdate());
				final String dateEnd = DateUtils.toYYYYMMDD(leave.getEnddate());
				leave.setStartdate(dateStart);
				leave.setEnddate(dateEnd);
				// applying for another person
				if (leave.getEmployeeid() != employeeId) {
					leave.setRequestedby(employeeId);
				} else {
					leave.setRequestedby(employeeId);
				}
				logger.info("FinancialYear altered=[" + leave.toString() + "]");
				// parse dates
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Exception parsing dates - " + e.toString());
			}
			// create
			final ResponseEntity<Leave> response = leaveServices.Create(authToken, leave);
			int code = response.getStatusCodeValue();
			System.out.println("Status Code ----> " + code);
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
			} else if (HttpStatusCodes.NOT_ACCEPTABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				return new ResponseEntity<Leave>(response.getBody(), HttpStatus.OK);
			}
		}catch (HttpClientErrorException ex) {
			final HttpStatus code = ex.getStatusCode();
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("HttpClientErrorException is {%s} ", ex.toString()));
			return new ResponseEntity<>(code);
		}catch (HttpServerErrorException  ex) {
			final HttpStatus code = ex.getStatusCode();
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("HttpServerErrorException is {%s} ", ex.toString()));
			return new ResponseEntity<>(code);
		}catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1/leave-update/{leaveid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> UpdateLeave(@PathVariable("leaveid") int leaveid, @RequestBody Leave leave) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int employeeId = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("Leave ID =[" + leaveid + "]");
			
			// parse dates
			// dd-mm-yyyy to yyyy-mm-dd
			try {
				final String dateStart = DateUtils.toYYYYMMDD(leave.getStartdate());
				final String dateEnd = DateUtils.toYYYYMMDD(leave.getEnddate());
				if (dateStart != null) {
					leave.setStartdate(dateStart);
				}
				if (dateEnd != null) {
					leave.setEnddate(dateEnd);
				}
				// applying for another person
				if (leave.getEmployeeid() != employeeId) {
					leave.setRequestedby(employeeId);
				} else {
					leave.setRequestedby(employeeId);
				}
				logger.info("Leave Data =[" + leave.toString() + "]");
				// parse dates
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Exception parsing dates - " + e.toString());
			}
			// id
			leave.setId(leaveid);
			logger.info("Leave Data =[" + leave.toString() + "]");
			// update
			final ResponseEntity<Leave> response = leaveServices.Update(authToken, leaveid, leave);
			int code = response.getStatusCodeValue();
			logger.info(String.format("Leave status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Leave status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			} else {
				return new ResponseEntity<Leave>(response.getBody(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1/leave-approve/{leaveid}/{status}/{comment}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody Integer ApproveLeave(@PathVariable("leaveid") int leaveid, @PathVariable("status") int status,
			@PathVariable("comment") String comment) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int employeeId = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("Leave Approval Parameters =[" + leaveid + " , " + status + " , " + comment +  " , " + employeeId + "]");
			// create
			final ResponseEntity<Void> response = leaveServices.Approve(authToken, leaveid, employeeId, status,
					comment);
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear approve status code {%d} is not equal to 200", code));
			logger.info(String.format("Status code {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return HttpStatus.ALREADY_REPORTED.value();
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return HttpStatus.NOT_FOUND.value();
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return HttpStatus.UNAUTHORIZED.value();
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return HttpStatus.NOT_IMPLEMENTED.value();
			} else {
				return HttpStatus.OK.value();
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
	}

	@RequestMapping(value = "/v1/leave-cancel/{leaveid}/{requesterid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody Integer CancelLeave(@PathVariable("leaveid") int leaveid,
			@PathVariable("requesterid") int requesterid) {
		logger.info("FinancialYear ID =[" + leaveid + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<Void> response = leaveServices.Delete(authToken, leaveid, requesterid);
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear cancel status code {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return HttpStatus.ALREADY_REPORTED.value();
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return HttpStatus.NOT_FOUND.value();
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return HttpStatus.UNAUTHORIZED.value();
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return HttpStatus.NOT_IMPLEMENTED.value();
			} else {
				return HttpStatus.OK.value();
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
	}
	
	@RequestMapping(value = "/v1/leave-delete/{leaveid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody Integer DeleteLeave(@PathVariable("leaveid") int leaveid) {
		logger.info("FinancialYear ID =[" + leaveid + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<Void> response = leaveServices.Delete(authToken, leaveid);
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear cancel status code {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return HttpStatus.ALREADY_REPORTED.value();
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return HttpStatus.NOT_FOUND.value();
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return HttpStatus.UNAUTHORIZED.value();
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return HttpStatus.NOT_IMPLEMENTED.value();
			} else {
				return HttpStatus.OK.value();
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return HttpStatus.INTERNAL_SERVER_ERROR.value();
		}
	}

	// leave balance
	@RequestMapping(value = "/v1/leave-balance-by-employee/{employeeid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetLeaveBalance(@PathVariable("employeeid") int employeeid) {
		logger.info("FinancialYear balance for employee ID =[" + employeeid + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<EmployeeLeaveBalance> response = leaveBalanceServices.GetAllByEmployee(authToken,
					employeeid);
			int code = response.getStatusCodeValue();

			logger.info(String.format("FinancialYear balance status code {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else {
				return new ResponseEntity<EmployeeLeaveBalance>(response.getBody(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1/leave-balance-by-supervisor/{supervisorid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetLeaveBalanceBySupervisor(@PathVariable("supervisorid") int supervisorid) {
		logger.info("FinancialYear balance for all employees by supervisor");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<EmployeeLeaveBalance[]> response = leaveBalanceServices.GetAllBySupervisor(authToken,
					supervisorid);
			int code = response.getStatusCodeValue();

			logger.info(String.format("FinancialYear balance status code {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else {
				return new ResponseEntity<EmployeeLeaveBalance[]>(response.getBody(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1/leave-balance", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetLeaveBalance() {
		logger.info("FinancialYear balance for all employees");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<EmployeeLeaveBalance[]> response = leaveBalanceServices.GetAll(authToken);
			int code = response.getStatusCodeValue();

			logger.info(String.format("FinancialYear balance status code {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
			} else {
				return new ResponseEntity<EmployeeLeaveBalance[]>(response.getBody(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1/leave-commutation-commute", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> CommuteLeave(@RequestBody LeaveCommutation commutation) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int employeeId = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("FinancialYear commutation = [" + commutation.toString() + "]");
			// parse dates
			// dd-mm-yyyy to yyyy-mm-dd
			try {
				final String dateCommute = DateUtils.toYYYYMMDD(commutation.getSolddate());
				if (dateCommute != null) {
					commutation.setSolddate(dateCommute);
				}
				logger.info("FinancialYear commutation date added = [" + commutation.toString() + "]");
				// parse dates
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Exception parsing dates - " + e.toString());
			}
			// create
			final ResponseEntity<?> response = leaveCommutationServices.Create(authToken, commutation, employeeId);
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(
						String.format("FinancialYear commutation status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			} else if (HttpStatusCodes.FAILED_DEPENDENCY == code) {
				return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
			} else if (HttpStatusCodes.LOOP_DETECTED == code) {
				return new ResponseEntity<>(HttpStatus.LOOP_DETECTED);
			} else {
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			}
		} catch (HttpServerErrorException ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			HttpStatus HttpServerErrorExceptionCode = ex.getStatusCode();
			int code1 = HttpServerErrorExceptionCode.LOOP_DETECTED.value();

			if (HttpStatusCodes.LOOP_DETECTED == code1) {
				return new ResponseEntity<>(HttpStatus.LOOP_DETECTED);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
	}

	@RequestMapping(value = "/v1/leave-commutation-update/{id}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> CommuteLeave(@PathVariable("id") int id,
			@RequestBody LeaveCommutation commutation) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int employeeId = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("FinancialYear commutation update =[" + commutation.toString() + "]");
			// create
			final ResponseEntity<LeaveCommutation> response = leaveCommutationServices.Update(authToken, id,
					commutation);
			int code = response.getStatusCodeValue();
			logger.info(String.format("FinancialYear status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(
						String.format("FinancialYear commutation status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			} else {
				return new ResponseEntity<LeaveCommutation>(response.getBody(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/v1/leave-commutation-delete/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> DeleteCommuteLeave(@PathVariable("id") int id) {

		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("Leve commutation ID =[" + id + "]");
			// create
			final ResponseEntity<LeaveCommutation> response = leaveCommutationServices.Delete(authToken, id);
			int code = response.getStatusCodeValue();
			logger.info(String.format("Leave status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Leave commutation status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			} else {
				return new ResponseEntity<LeaveCommutation>(response.getBody(), HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	@RequestMapping(value = "/v1/leave-commutation-amount/{days}/{employeeid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> ApplyLeave(@PathVariable("days") int days, @PathVariable("employeeid") int employeeid) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int employeeId = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("Days =[" + days + "]" + " Emp =[" + employeeId + "]");
			// create
			final ResponseEntity<Double> response = leaveCommutationServices.GetAmount(authToken, employeeid, days);
			int code = response.getStatusCodeValue();
			logger.info(String.format("Status is {%d} ", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Leave status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Double>(response.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// View Training Details
	@RequestMapping(value = "/v1/leave-approval-notofication/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetLeavepprovalStatus(@PathVariable("id") int id) {
		logger.info("Leave ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<LeaveApprovalStatus[]> response = leaveServices.GetLeaveApprovers(authToken, id);
			if (response != null) {
//				logger.info(String.format("View Leave Details {%s}", response.getBody()));
				return new ResponseEntity<LeaveApprovalStatus[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@RequestMapping(value = "/v1/leave-commutation-notification/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetLeaveCommutationNotification(@PathVariable("id") int id) {
		logger.info("Leave Commutation ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<TrainingApprovalStatus[]> response = leaveCommutationServices
					.GetLeaveCommutationNotification(authToken, id);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<TrainingApprovalStatus[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/leave-commutation-approval.htm", method = RequestMethod.GET)
	public ModelAndView LeaveCommutationApproveBySupervisor() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<LeaveCommutation[]> response = leaveCommutationServices.
					GetLeaveCommutationNotApprovedBySupervisorNext(authToken,employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("LeaveCommutation status is {%d}", code));
				LeaveCommutation[] _leaveCommutation = response.getBody();
				int approvalCount = _leaveCommutation.length;
				
				System.out.println("ApprovalCount --> " + approvalCount);
				
				mv = new ModelAndView("leave/leave-commutation-approval");
				mv.addObject("approvalCount", approvalCount);
				mv.addObject("leaveCommutation", _leaveCommutation);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}

	@RequestMapping(value = "/v1/employee-leave-commutation-notification/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeLeaveNotification(@PathVariable("id") int id) {
		logger.info("Leave Commutation ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<TrainingApprovalStatus[]> response = leaveCommutationServices
					.GetLeaveCommutationNotification(authToken, id);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<TrainingApprovalStatus[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/acknowledge-leave-commutation/{id}/{acknowledge}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> AcknowledgeLeaveCommutation(@PathVariable("id") int id, @PathVariable("acknowledge") int acknowledge) {
		logger.info("Leave Commutation ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int empId = authenticationFacade.getUser().getEmpId();
 			// create
			final ResponseEntity<LeaveCommutation> response = leaveCommutationServices.AcknowledgeLeaveCommutation(authToken, id, empId, acknowledge);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<LeaveCommutation>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/employee-on-leave.htm", method = RequestMethod.GET)
	public ModelAndView EmployeeOnLeave() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {		
			mv = new ModelAndView("leave/employee-on-leave");
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}
	
	@RequestMapping(value = "/v1/employee-on-leave/{startdate}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> EmployeeOnLeave(@PathVariable("startdate") String startdate) {
		logger.info("Leave Commutation ID =[" +startdate + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int empId = authenticationFacade.getUser().getEmpId();
 			// create
			final ResponseEntity<Leave[]> response = leaveServices.GetAllLeaveApplicationsApprovedBySupervisorId(authToken, empId, startdate);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<Leave[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// View Training Details
	@RequestMapping(value = "/v1/leave-commutation-approval/{id}/{status}/{comment}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> LeaveCommutationApproval(@PathVariable("id") int id,
			@PathVariable("status") int approvalStatus, @PathVariable("comment") String comment) {
		logger.info("Leave ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int empId = authenticationFacade.getUser().getEmpId();
			// create
			Comment commentEntity = new Comment();
			commentEntity.setComment(comment);
			final ResponseEntity<Comment> response = leaveCommutationServices.ApproveLeaveCommutationV2(authToken, id, empId, approvalStatus,commentEntity );
			if (response != null) {
//				logger.info(String.format("View Leave Details {%s}", response.getBody()));
				return new ResponseEntity<Comment>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (HttpServerErrorException ex) {
			ex.printStackTrace();
			HttpStatus HttpServerErrorExceptionCode = ex.getStatusCode();
			int NOT_IMPLEMENTED = HttpServerErrorExceptionCode.NOT_IMPLEMENTED.value();
		
			if(NOT_IMPLEMENTED == HttpStatusCodes.NOT_IMPLEMENTED) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/v1/my-leave-balance", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> LeaveEmployeeBalance() {
		logger.info("Leave Balance");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int employeeID = authenticationFacade.getUser().getEmpId();
 			// create
			final ResponseEntity<EmployeeLeaveBalance> response =  leaveBalanceServices.GetAllByEmployee(authToken,employeeID);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<EmployeeLeaveBalance>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/employee-leave-application.htm", method = RequestMethod.GET)
	public ModelAndView EmployeeLeaveApplication() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {	
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int empId = authenticationFacade.getUser().getEmpId();
 			// create
			final ResponseEntity<Leave[]> response = leaveServices.GetAllLeaveApplicationsByEmployeeID(authToken, empId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("Employee Leave Application Status is {%d}", code));
				Leave[] _leave = response.getBody();
				mv = new ModelAndView("leave/employee-leave-application");
				mv.addObject("empLeave", _leave);
			}			
			
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}
	
	@RequestMapping(value = "/v1/employee-leave-application", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeByID() {
		logger.info("Leave Commutation ID ");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int empId = authenticationFacade.getUser().getEmpId();
 			// create
			final ResponseEntity<Leave[]> response = leaveServices.GetAllLeaveApplicationsByEmployeeID(authToken, empId);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<Leave[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@RequestMapping(value = "/v1/employee-leave-approval-get-byID/{leaveid}", method = RequestMethod.GET)
//	public @ResponseBody ResponseEntity<?> GetEmployeeLeaveApprovalByID(@PathVariable("leaveid") int leaveid) {
//		System.out.println("Leave approval ID -----> " + leaveid);
//		logger.info("Leave approval ID ");
//		try {
//			final String authToken = authenticationFacade.getAuthenticationToken();
//		
// 			// create
//			final ResponseEntity<Leave> response = leaveServices.Get(authToken, leaveid);
//			if (response != null) {
//				logger.info(String.format("View Leave Approval ", response.getBody()));
//				return new ResponseEntity<Leave>(response.getBody(), HttpStatus.OK);
//			}
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		} catch (HttpClientErrorException ex) {
//			ex.printStackTrace();
//			HttpStatus status = ex.getStatusCode();
//			int code = status.value();
//			logger.info(String.format("Status {%d} is not equal to 200", code));
//			if (HttpStatusCodes.EXISTS == code) {
//				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
//						HttpStatus.ALREADY_REPORTED);
//			} else if (HttpStatusCodes.NOT_FOUND == code) {
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
//				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
//			}
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			logger.error(String.format("Exception is {%s} ", ex.toString()));
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@RequestMapping(value = "/v1/employee-leave-approval-get-byID/{leaveid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeLeaveApprovalByID(@PathVariable("leaveid") int leaveid) {
		logger.info("Leave Commutation ID =[" + leaveid + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<Leave> response = leaveServices.Get(authToken, leaveid);
			if (response != null) {
				logger.info(String.format("View Leave Commutation ", response.getBody()));
				return new ResponseEntity<Leave>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Leave Recall

	@RequestMapping(value = "/v1/leave-recall", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> ApplyLeave(@RequestBody Recall recall) {
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final int requestedID = user.getEmpId();
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			logger.info("Leave Recall Data =[" + recall.toString() + "]");
			// parse dates
			// dd-mm-yyyy to yyyy-mm-dd
		
			try {
//				final String dateStart = DateUtils.toYYYYMMDD(recall.getDateexpectedstart());
//				final String dateEnd = DateUtils.toYYYYMMDD(recall.getDate_expected_end());
				recall.setStartdate(recall.getDateexpectedstart());
				recall.setEnddate(recall.getDate_expected_end());
		
				
				// applying for another person
				//recall.setRequestedbyid(requestedID);
				
				logger.info("Leave Recall altered =[" + recall.toString() + "]");
				// parse dates
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Exception parsing dates - " + e.toString());
			}
			// create
			final ResponseEntity<Recall> response = leaveRecallServices.Create(authToken, recall, requestedID);
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
			} else if (HttpStatusCodes.SUCCESS == code) {
				return new ResponseEntity<Recall>(response.getBody(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			logger.info(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/leave-recall-management.htm", method = RequestMethod.GET)
	public ModelAndView LeaveRecallManagement() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<Recall[]> response = leaveRecallServices.GetAllBySupervisor(authToken, employeeId);
			

			
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear balance status is {%d}", code));
				Recall[] _leaveRecall = response.getBody();
				
				mv = new ModelAndView("leave/leave-recall-management");
				
			
				mv.addObject("leaveRecall", _leaveRecall);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mv = new ModelAndView("error");
		}
		return mv;
	}
	
	@RequestMapping(value = "/v1/leave-recall-notification/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetLeaveRecallNotification(@PathVariable("id") int id) {
		logger.info("Leave Recall ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<TrainingApprovalStatus[]> response = leaveRecallServices.GetLeaveRecallNotification(authToken, id);
			
			if (response != null) {
				logger.info(String.format("View Leave Recall ", response.getBody()));
				return new ResponseEntity<TrainingApprovalStatus[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/leave-applications-recall.htm", method = RequestMethod.GET)
	public ModelAndView LeaveRecall() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<Recall[]> response = leaveRecallServices.GetAllBySupervisorNext(authToken, employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear recall status is {%d}", code));
				Recall[] _leaveRecall = response.getBody();
				mv = new ModelAndView("leave/leaves-recall");
				mv.addObject("leaveRecall", _leaveRecall);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}
	
	@RequestMapping(value = "/leave-recall-by-employeeId.htm", method = RequestMethod.GET)
	public ModelAndView EmployeeLeaveRecall() {
		// mv
		ModelAndView mv = null;
		// leaves
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final int employeeId = user.getEmpId();
			final ResponseEntity<Recall[]> response = leaveRecallServices.GetByEmployeeID(authToken, employeeId);
			if (response != null && response.getBody() != null) {
				int code = response.getStatusCodeValue();
				logger.info(String.format("FinancialYear recall status is {%d}", code));
				Recall[] _leaveRecall = response.getBody();
				mv = new ModelAndView("leave/employee-leave-recall");
				mv.addObject("leaveRecall", _leaveRecall);
			}
		} catch (Exception ex) {
			mv = new ModelAndView("error");
		}
		return mv;
	}
	
	@RequestMapping(value = "/v1/employee-leave-recall-notification/{id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> GetEmployeeLeaveRecallNotification(@PathVariable("id") int id) {
		logger.info("Leave Recall Notification ID =[" + id + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			// create
			final ResponseEntity<TrainingApprovalStatus[]> response = leaveRecallServices.GetLeaveRecallNotification(authToken, id);
			if (response != null) {
				logger.info(String.format("View Leave R ", response.getBody()));
				return new ResponseEntity<TrainingApprovalStatus[]>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/v1/acknowledge-leave-recall/{id}/{acknowledge}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody ResponseEntity<?> AcknowledgeLeaveRecall(@PathVariable("id") int id, @PathVariable("acknowledge") int acknowledge) {
		logger.info("Leave Recall ID =[" + id +  " --> "+ acknowledge + "]");
		try {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final int empId = authenticationFacade.getUser().getEmpId();
			
			logger.info("Leave Recall ID =[" + id +  " --> "+ acknowledge + " --> "+ empId +"]");
 			// create
			final ResponseEntity<Recall> response = leaveRecallServices.AcknowledgeLeaveRecall(authToken, id, empId, acknowledge);
			if (response != null) {
				logger.info(String.format("View Leave Recall ", response.getBody()));
				return new ResponseEntity<Recall>(response.getBody(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (HttpClientErrorException ex) {
			ex.printStackTrace();
			HttpStatus status = ex.getStatusCode();
			int code = status.value();
			logger.info(String.format("Status {%d} is not equal to 200", code));
			if (HttpStatusCodes.EXISTS == code) {
				return new ResponseEntity<>(String.format("Status code is {%d} already exists", code),
						HttpStatus.ALREADY_REPORTED);
			} else if (HttpStatusCodes.NOT_FOUND == code) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
				return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(String.format("Exception is {%s} ", ex.toString()));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}



package com.tcra.hrms.controller.training;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.config.Utility;
import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.core.HttpStatusCodes;
import com.tcra.hrms.dto.Comment;
import com.tcra.hrms.dto.Currency;
import com.tcra.hrms.dto.Employee;
import com.tcra.hrms.dto.training.TrainingResponse;
import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;
import com.tcra.hrms.entity.training.FinancialYear;
import com.tcra.hrms.entity.training.HrmsTraining;
import com.tcra.hrms.entity.training.HrmsTrainingCategory;
import com.tcra.hrms.entity.training.HrmsTrainingInitiator;
import com.tcra.hrms.entity.training.HrmsTrainingSponsor;
import com.tcra.hrms.entity.training.HrmsTrainingType;
import com.tcra.hrms.entity.training.TrainingApprovalStatus;
import com.tcra.hrms.services.IEmployeeServices;
//import com.tcra.hrms.services.training.ICurrency;
import com.tcra.hrms.services.training.IFinancialYearServices;
import com.tcra.hrms.services.training.ITrainingService;

@Controller
public class TrainingController {
	

	@Autowired
	private ITrainingService trainingService;
	@Autowired
	private IFinancialYearServices financialYear;
	@Autowired
	private IAuthenticationFacade authenticationFacade;
//	@Autowired
//	private ICurrency currencyService;

	
	@Autowired
	private IEmployeeServices employeeServices;

	private static final Logger logger = Logger.getLogger(TrainingController.class); // log4j

	@RequestMapping(value = "/training.htm", method = RequestMethod.GET)
	public ModelAndView Training() {
		List<TrainingResponse> training = null;
		logger.info("Get Training information for the Current Financial Year");
		final UserAuthenticationPrincipal user = authenticationFacade.getUser();
		final Authentication auth = authenticationFacade.getAuthentication();
		final int employeeID = user.getEmpId();
		if (trainingService != null) {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<TrainingResponse[]> _training = trainingService.GetTrainingCurrentFinancialYear(authToken, employeeID);

			if (_training != null && _training.getBody() != null) {

				training = Arrays.asList(_training.getBody());
			}
		}
		
		// employees
		ArrayList<Employee> _employeesActing = null;
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
		ModelAndView mv = new ModelAndView("training/training");
		mv.addObject("training", training);
		mv.addObject("employees", _employees);
		return mv;
	}
	
	
	@RequestMapping(value = "/training-category.htm", method = RequestMethod.GET)
	public ModelAndView TrainingCategory() {
		List<HrmsTrainingCategory> trainingCategory = null;

		if (trainingService != null) {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<HrmsTrainingCategory[]> _trainingCategory = trainingService
					.GetTrainingCategory(authToken);

			if (_trainingCategory != null && _trainingCategory.getBody() != null) {
				trainingCategory = Arrays.asList(_trainingCategory.getBody());
			}
		}

		ModelAndView mv = new ModelAndView("training/training-category");
		mv.addObject("trainingCategory", trainingCategory);
		return mv;
	}

	@RequestMapping(value = "/training-initiator.htm", method = RequestMethod.GET)
	public ModelAndView TrainingInitiator() {

		List<HrmsTrainingInitiator> trainingInitiator = null;

		if (trainingService != null) {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<HrmsTrainingInitiator[]> _trainingInitiator = trainingService
					.GetTrainingInitiator(authToken);

			if (_trainingInitiator != null && _trainingInitiator.getBody() != null) {
				trainingInitiator = Arrays.asList(_trainingInitiator.getBody());
			}
		}
		ModelAndView mv = new ModelAndView("training/training-initiator");
		mv.addObject("trainingInitiator", trainingInitiator);
		return mv;
	}

	@RequestMapping(value = "/training-sponsor.htm", method = RequestMethod.GET)
	public ModelAndView TrainingSponsor() {
		List<HrmsTrainingSponsor> trainingSponsor = null;

		if (trainingService != null) {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<HrmsTrainingSponsor[]> _trainingSponsor = trainingService
					.GetTrainingSponsor(authToken);

			if (_trainingSponsor != null && _trainingSponsor.getBody() != null) {
				trainingSponsor = Arrays.asList(_trainingSponsor.getBody());
			}
		}
		ModelAndView mv = new ModelAndView("training/training-sponsor");
		mv.addObject("trainingSponsor", trainingSponsor);
		return mv;
	}

	@RequestMapping(value = "/training-type.htm", method = RequestMethod.GET)
	public ModelAndView TrainingType() {
		List<HrmsTrainingType> trainingType = null;

		if (trainingService != null) {
			final String authToken = authenticationFacade.getAuthenticationToken();
			final ResponseEntity<HrmsTrainingType[]> _trainingType = trainingService.GetTrainingType(authToken);

			if (_trainingType != null && _trainingType.getBody() != null) {
				trainingType = Arrays.asList(_trainingType.getBody());
			}
		}
		ModelAndView mv = new ModelAndView("training/training-type");
		mv.addObject("trainingType", trainingType);
		return mv;
	}

	// Retrieve Currency JSON
	@RequestMapping(value = "/get-training-currency-listAjax")
	public @ResponseBody Object GetTrainingCurrencyList() {

		final String authToken = authenticationFacade.getAuthenticationToken();	
		final ResponseEntity<Currency[]> listCurrency = trainingService.GetAllCurrency(authToken);

		if (listCurrency.getBody() != null) {
			final List<Currency> _listCurrency = Arrays.asList(listCurrency.getBody());
			return _listCurrency;
		}
		return null;
	}

	// Retrieve training category JSON
	@RequestMapping(value = "/get-training-category-listAjax")
	public @ResponseBody Object GetTrainingCategoryList() {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<HrmsTrainingCategory[]> listCategory = trainingService.GetTrainingCategory(authToken);
		if (listCategory.getBody() != null) {
			final List<HrmsTrainingCategory> _listCategory = Arrays.asList(listCategory.getBody());
			return _listCategory;
		}
		return null;
	}

	// Retrieve training Initiator JSON
	@RequestMapping(value = "/get-training-initiation-listAjax")
	public @ResponseBody Object GetTrainingInitiationList() {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<HrmsTrainingInitiator[]> listInitiation = trainingService.GetTrainingInitiator(authToken);
		if (listInitiation.getBody() != null) {
			final List<HrmsTrainingInitiator> _listInitiation = Arrays.asList(listInitiation.getBody());
			return _listInitiation;
		}
		return null;
	}

	// Retrieve training Initiator JSON
	@RequestMapping(value = "/get-training-type-listAjax")
	public @ResponseBody Object GetTrainingTypeList() {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<HrmsTrainingType[]> listType = trainingService.GetTrainingType(authToken);
		if (listType.getBody() != null) {
			final List<HrmsTrainingType> _listType = Arrays.asList(listType.getBody());
			return _listType;
		}
		return null;
	}

	// Retrieve training sponsor JSON
	@RequestMapping(value = "/get-training-sponsor-listAjax")
	public @ResponseBody Object GetTrainingSponsorList() {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<HrmsTrainingSponsor[]> listSponsor = trainingService.GetTrainingSponsor(authToken);
		if (listSponsor.getBody() != null) {
			final List<HrmsTrainingSponsor> _listSponsor = Arrays.asList(listSponsor.getBody());
			return _listSponsor;
		}
		return null;
	}

	// Retrieve training sponsor JSON
	@RequestMapping(value = "/get-financial-year-listAjax")
	public @ResponseBody Object GetFinancialYearList() {
		final String authToken = authenticationFacade.getAuthenticationToken();
		final ResponseEntity<FinancialYear[]> listFinacialYear = financialYear.GetAll(authToken);
		if (listFinacialYear.getBody() != null) {
			final List<FinancialYear> _listFinacialYear = Arrays.asList(listFinacialYear.getBody());
			return _listFinacialYear;
		}
		return null;
	}
	
	
	//Retrieve employees JSON
	@RequestMapping(value = "/get-employees-listAjax")
	public @ResponseBody Object GetEmployeeList() {
		final ResponseEntity<Employee[]> employeeResult = employeeServices.GetAll();
		if(employeeResult.getBody() != null) {
			final List<Employee> employees = Arrays.asList(employeeResult.getBody());
			return employees;
		}
		return null;
	}
	
	// Add Training and upload file using Ajax
	
		@RequestMapping(value = "/v1/training-add", method = RequestMethod.POST)
		public @ResponseBody ResponseEntity<?> addTraining(Model model, @ModelAttribute("training") HrmsTraining training,
				BindingResult bindingResult, @RequestParam("feestructureattachment") MultipartFile fileUpload,
				HttpServletRequest request, RedirectAttributes redirectAttributes) {
			
			logger.info(String.format("Training data passed is {%s} ", training));
			logger.info(String.format("Attachment file name {%s} ", fileUpload.getOriginalFilename()));
			try {
				
				if (training == null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				ResponseEntity<HrmsTraining> response = null;
				Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
				Long id = timestamp2.getTime();
				
				String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;

				training.setFeestructureattachment(AppConstants.URL + newFileName);
				training.setTrainingpurposeid(2);
				training.setTraininginitiatorid(3);
				final String authToken = authenticationFacade.getAuthenticationToken();

				if(fileUpload.isEmpty()) {				
					response = trainingService.CreateTraining(authToken, training);
				}

				if (extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png")
						|| extensionType.toLowerCase().equals("jpeg")) {

					System.out.println(fileURL);
					boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
					response = trainingService.CreateTraining(authToken, training);
				}
				if (response == null) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				int code = response.getStatusCodeValue();
				if (HttpStatusCodes.EXISTS == code) {
					// read retrieved data for already reported payroll
					return new ResponseEntity<HrmsTraining>(response.getBody(), HttpStatus.ALREADY_REPORTED);
				} else if (HttpStatusCodes.NOT_FOUND == code) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
					return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
				} else {
					logger.info(String.format("Training Successfully Added {%s}", response.getBody()));
					return new ResponseEntity<HrmsTraining>(response.getBody(), HttpStatus.OK);
				}
			} catch (Exception ex) {
				System.out.print(ex.toString());
				ex.printStackTrace();
				logger.error(String.format("Exception is {%s} ", ex.toString()));
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		// View Training Details
		@RequestMapping(value = "/v1/training-view-details/{trainingId}", method = RequestMethod.GET, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		public @ResponseBody ResponseEntity<?> GetTraningDetails(@PathVariable("trainingId") int trainingId) {
			logger.info("Training Detail ID =[" + trainingId + "]");
			try {
				final String authToken = authenticationFacade.getAuthenticationToken();
				// create
				final ResponseEntity<TrainingResponse> response = trainingService.GetTrainingDetails(authToken, trainingId);
				if (response != null) {
					logger.info(String.format("View Training Details {%s}", response.getBody()));
					return new ResponseEntity<TrainingResponse>(response.getBody(), HttpStatus.OK);
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

		// Update Training
		@RequestMapping(value = "/v1/training-update/{id}", method = RequestMethod.POST)
		public @ResponseBody ResponseEntity<?> updateTraining(
				@PathVariable("id") int id,
				Model model, @ModelAttribute("updateTraining") HrmsTraining hrmsTraining,
				BindingResult bindingResult, @RequestParam("feestructureattachment") MultipartFile fileUpload,
				HttpServletRequest request, RedirectAttributes redirectAttributes
				
				) {
			
			logger.info(String.format("Update Training data passed is {%s} ", hrmsTraining));
			logger.info(String.format("Attachment file name {%s} ", fileUpload.getOriginalFilename()));
			try {
				if (hrmsTraining == null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
				ResponseEntity<HrmsTraining> response = null;
				Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
				Long _id = timestamp2.getTime();
				
				String extensionType = FilenameUtils.getExtension(fileUpload.getOriginalFilename());
				String fileName = Utility.trimFileExtension(fileUpload.getOriginalFilename());
				String newFileName = fileName + "_" + _id + "." + extensionType;
				String fileURL = AppConstants.UPLOAD_DIRECTORY + newFileName;

				hrmsTraining.setFeestructureattachment(AppConstants.URL + newFileName);
				hrmsTraining.setTrainingpurposeid(2);
				hrmsTraining.setTraininginitiatorid(3);
				final String authToken = authenticationFacade.getAuthenticationToken();

				if(fileUpload.isEmpty()) {				
					response =trainingService.UpdateTraining(authToken, hrmsTraining, id);
				}

				if (extensionType.toLowerCase().equals("pdf") || extensionType.toLowerCase().equals("png")
						|| extensionType.toLowerCase().equals("jpeg")) {

					System.out.println(fileURL);
					boolean isFileUpload = Utility.uploadFile(fileUpload, fileURL, redirectAttributes);
					response = trainingService.UpdateTraining(authToken, hrmsTraining, id);
				}
				if (response == null) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				int code = response.getStatusCodeValue();
				if (HttpStatusCodes.EXISTS == code) {
					// read retrieved data for already reported payroll
					return new ResponseEntity<HrmsTraining>(response.getBody(), HttpStatus.ALREADY_REPORTED);
				} else if (HttpStatusCodes.NOT_FOUND == code) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
					return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
				} else {
					logger.info(String.format("Training Successfully Added {%s}", response.getBody()));
					return new ResponseEntity<HrmsTraining>(response.getBody(), HttpStatus.OK);
				}
			} catch (Exception ex) {
				System.out.print(ex.toString());
				ex.printStackTrace();
				logger.error(String.format("Exception is {%s} ", ex.toString()));
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// Delete Training
		@RequestMapping(value = "/v1/training-delete/{trainingId}", method = RequestMethod.GET, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		public @ResponseBody ResponseEntity<?> DeleteTraningDetails(@PathVariable("trainingId") int trainingId) {
			logger.info("Training Detail ID =[" + trainingId + "]");
			try {
				final String authToken = authenticationFacade.getAuthenticationToken();
				// create
				final ResponseEntity<HrmsTraining> response = trainingService.DeleteTraining(authToken, trainingId);
				if (response != null) {
					logger.info(String.format("View Training Details {%s}", response.getBody()));
					return new ResponseEntity<HrmsTraining>(response.getBody(), HttpStatus.OK);
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
		
		// Get Training Information Status
		@RequestMapping(value = "/training-status.htm", method = RequestMethod.GET)
		public ModelAndView TrainingStatus() {
			List<TrainingResponse> training = null;
			logger.info("Get Training Status information for the Current Financial Year");
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final Authentication auth = authenticationFacade.getAuthentication();
			final int employeeID = user.getEmpId();
			if (trainingService != null) {
				final String authToken = authenticationFacade.getAuthenticationToken();
				final ResponseEntity<TrainingResponse[]> _training = trainingService.GetTrainingCurrentFinancialYear(authToken, employeeID);
				
//				logger.info(String.format("View Training Details Status {%s}", _training.getBody()));
				if (_training != null && _training.getBody() != null) {
					training = Arrays.asList(_training.getBody());
					
				}
			}
			
			// employees
			ArrayList<Employee> _employeesActing = null;
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
			ModelAndView mv = new ModelAndView("training/training-status");
			mv.addObject("training", training);
			mv.addObject("employees", _employees);
			return mv;
		}
		
		// Get Training Information for Approval
		@RequestMapping(value = "/training-approval.htm", method = RequestMethod.GET)
		public ModelAndView TrainingApproval() {
			List<TrainingResponse> training = null;
			logger.info("Get Training Status information for the Current Financial Year");
			final UserAuthenticationPrincipal user = authenticationFacade.getUser();
			final Authentication auth = authenticationFacade.getAuthentication();
			final int employeeID = user.getEmpId();
			//final int employeeID = 588;
			if (trainingService != null) {
				final String authToken = authenticationFacade.getAuthenticationToken();
				final ResponseEntity<TrainingResponse[]> _training = trainingService.GetTrainingNotApprovedBySupervisor(authToken, employeeID);

				if (_training != null && _training.getBody() != null) {
					training = Arrays.asList(_training.getBody());
				}
			}
			
			// employees
			ArrayList<Employee> _employeesActing = null;
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
			ModelAndView mv = new ModelAndView("training/training-approval");
			mv.addObject("training", training);
			mv.addObject("employees", _employees);
			return mv;
		}
		
		// Training Approve
		@RequestMapping(value = "/v1/training-approval/{id}/{comment}/{status}", method = RequestMethod.POST)
		public @ResponseBody ResponseEntity<?> TrainingApprove(
				@PathVariable("id") int id, @PathVariable("comment") String comment, @PathVariable("status") int status) {		
				
			logger.info(String.format("Training Approval parameters {%s} ", id + " --> " +  comment + " --> " + status));			
			try {
				if (id == 0 && comment == null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				Comment commentEntity = new Comment();
				commentEntity.setComment(comment);
				final String authToken = authenticationFacade.getAuthenticationToken();
				int trainingid = id;
				//int supervisorid = 588;
				final UserAuthenticationPrincipal user = authenticationFacade.getUser();
				
				int supervisorid = user.getEmpId();
				ResponseEntity<Comment> response = trainingService.ApproveTraining(authToken, commentEntity, trainingid, status, supervisorid);

				if (response == null) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				int code = response.getStatusCodeValue();
				if (HttpStatusCodes.EXISTS == code) {
					// read retrieved data for already reported payroll
					return new ResponseEntity<Comment>(response.getBody(), HttpStatus.ALREADY_REPORTED);
				} else if (HttpStatusCodes.NOT_FOUND == code) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				} else if (HttpStatusCodes.NOT_AUTHORIZED == code) {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				} else if (HttpStatusCodes.NOT_UNAVAILABLE == code) {
					return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
				} else {
					logger.info(String.format("Training Successfully Approved {%s}", response.getBody()));
					return new ResponseEntity<Comment>(response.getBody(), HttpStatus.OK);
				}
			} catch (Exception ex) {
				System.out.print(ex.toString());
				ex.printStackTrace();
				logger.error(String.format("Exception is {%s} ", ex.toString()));
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// View Training Details
		@RequestMapping(value = "/v1/training-status/{trainingId}", method = RequestMethod.GET, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		public @ResponseBody ResponseEntity<?> GetTraningApprovalStatus(@PathVariable("trainingId") int trainingId) {
			logger.info("Training Detail ID =[" + trainingId + "]");
			try {
				final String authToken = authenticationFacade.getAuthenticationToken();
				// create
				final ResponseEntity<TrainingApprovalStatus[]> response = trainingService.GetTrainingApprovalStatus(authToken, trainingId);
				if (response != null) {
					logger.info(String.format("View Training Details {%s}", response.getBody()));
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
}

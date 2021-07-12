package com.tcra.hrms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcra.hrms.auth.IAuthenticationFacade;
import com.tcra.hrms.entity.DashboardEmployeeStatistics;
import com.tcra.hrms.entity.UserEntity;
import com.tcra.hrms.services.IDashboardService;
import com.tcra.hrms.services.ILoginServices;

@Controller
public class HomeController {
	@Autowired
	private IDashboardService dashboardService;
	
	@Autowired
	private DashboardEmployeeStatistics dashboardEmployeeStatistics;

	@Autowired
	private ILoginServices loginServices;
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@RequestMapping(value = "/login.htm", method = RequestMethod.GET)
	public String login(Model model) {		
		model.addAttribute("login", new UserEntity()); 
		return "login";
	}
	
	@RequestMapping(value = { "/authenticate.htm" }, method = RequestMethod.POST)
	public String loginAction(@ModelAttribute("login") @Valid UserEntity userEntity, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {

		if (bindingResult.hasErrors()) {

			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.login", bindingResult);
			redirectAttributes.addFlashAttribute("login", new UserEntity());
			return "redirect:/Login";

		} else {
			// String token = authenticationFacade.getAuthenticationToken();
			ResponseEntity<UserEntity> result = loginServices.Login(userEntity);

			if (result != null) {
				//redirectAttributes.addFlashAttribute("success", "Login Succesfully");
				System.out.println(result.getHeaders().getFirst("Authorization"));
				uploadUserAttributesToSession(result, request, model);
				 loadLookup(request,model);
				return "redirect:/dashboard.htm";
			} else {
				redirectAttributes.addFlashAttribute("error", "Incorrect email or password!");
				return "redirect:/";
			}
		}
	}
	
	private void uploadUserAttributesToSession(ResponseEntity<UserEntity> result, HttpServletRequest request,
			Model model) {
		request.getSession().setAttribute("email", result.getBody().getEmail());
		request.getSession().setAttribute("fullname", result.getBody().getFullname());
		request.getSession().setAttribute("filenumber", result.getBody().getFilenumber());
		request.getSession().setAttribute("empId", result.getBody().getEmpId());
		request.getSession().setAttribute("photo", result.getBody().getPhoto());
		request.getSession().setAttribute("token", result.getHeaders().getFirst("Authorization"));

		//loadLookup(request, model);

	}
	
	//allow supervisor
	@RequestMapping(value = "/dashboard.htm", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request,Model model) {		

		 try {			  
			  String token = authenticationFacade.getAuthenticationToken();
			  loadLookup(request,model);
			  return "dashboard";
		  }catch(Exception er) {
			  er.printStackTrace();
			  return "redirect:/";
		  }
	}

	@RequestMapping(value="/Logout.htm", method={RequestMethod.GET, RequestMethod.POST})
	public String Logout(HttpServletResponse response, HttpServletRequest request,
							final RedirectAttributes redirectAttributes) {
		
		request.getSession().setAttribute("email", null);
		request.getSession().setAttribute("fullname", null);
		request.getSession().setAttribute("filenumber", null);
		request.getSession().setAttribute("token", null);
		request.getSession().setAttribute("photo", null);
		request.getSession().invalidate();
		//redirectAttributes.addFlashAttribute("message", "We hope to see you again as soon.");
		return "redirect:/";
	}


	private void loadLookup(HttpServletRequest request, Model model) {
		
		String token = authenticationFacade.getAuthenticationToken();
		int empID = authenticationFacade.getUser().getEmpId();
		
		ResponseEntity<DashboardEmployeeStatistics> response = 	dashboardService.GetDahboardOverall(empID, token);
		dashboardEmployeeStatistics = response.getBody();
		model.addAttribute("dashboardEmployeeStatistics", dashboardEmployeeStatistics);
	}
}

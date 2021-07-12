package com.tcra.hrms.configuration;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class HttpStatusCode {

	public static void getStatusRedirect(int statusCode, RedirectAttributes redirectAttributes) {
		switch(statusCode) {
		  case 404:
			  redirectAttributes.addFlashAttribute("error", "Content Not Found");
		    break;
		  case 424:
			  redirectAttributes.addFlashAttribute("error", "Mandatory Field Missing");
		    break;
		  case 208:
			  redirectAttributes.addFlashAttribute("error", "Duplicate Entry");
		    break;
		  case 200:
			  redirectAttributes.addFlashAttribute("success", "Request Successfully Processed");
		    break;	
		  case 412:
			  redirectAttributes.addFlashAttribute("error", "Precondition Failed");
		    break;
		  case 423:
			  redirectAttributes.addFlashAttribute("error", "Accont has been temporary locked. Please try after 5 minutes");
		    break;
		  default:
			  redirectAttributes.addFlashAttribute("error", "System Error");
		}
	}
}

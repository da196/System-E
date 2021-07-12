package com.tcra.hrms.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GlobalExceptionController {

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("error", "URL not valid");
		return "error";
	}
}

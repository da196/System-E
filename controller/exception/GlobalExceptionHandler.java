package com.tcra.hrms.controller.exception;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class); // log4j
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleIOException(Exception exception) {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("error");
		return andView;
	}
	
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(IOException exception) {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("error");
		return andView;
	}

	@ExceptionHandler(SQLException.class)
	public ModelAndView handleSQLException(SQLException exception) {
		ModelAndView andView = new ModelAndView();
		andView.setViewName("error");
		return andView;
	}
}

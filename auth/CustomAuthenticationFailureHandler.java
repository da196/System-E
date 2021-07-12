package com.tcra.hrms.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMessage="";
        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)){
        	errorMessage="Invalid username or password.";
        }else{
        	errorMessage="Unknown error - "+exception.getMessage();
        }
        request.getSession().setAttribute("message", errorMessage);
        // redirect to login
        String path = request.getContextPath()+"/login.htm";
		UriComponents uri = UriComponentsBuilder
				.newInstance()
				.path(path)
				.build();
        response.sendRedirect(uri.toUriString()); // Redirect user to login page with error message.		
	}

}

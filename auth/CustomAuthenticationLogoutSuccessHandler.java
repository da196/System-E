package com.tcra.hrms.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CustomAuthenticationLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// redirect to login
		String path = request.getContextPath()+"/login.htm";
		UriComponents uri = UriComponentsBuilder
				.newInstance()
				.path(path)
				.build();
        response.sendRedirect(uri.toUriString()); // Redirect user to login page with message.
	}

}

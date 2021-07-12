package com.tcra.hrms.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// path to home UI
		String path = request.getContextPath()+"/employee-personal-information.htm";
		
		// principal
		UserAuthenticationPrincipal principal = (UserAuthenticationPrincipal) authentication.getPrincipal();
		if(principal!=null) {
			System.out.println("CustomAuthenticationSuccessHandler >> "+principal.getFullname()); // debug
		}
		authenticationFacade = new AuthenticationFacade();
		if(authenticationFacade.getAuthentication()!=null && authenticationFacade.getAuthentication().getPrincipal()!=null) {
			UserAuthenticationPrincipal _principal = (UserAuthenticationPrincipal)authenticationFacade.getAuthentication().getPrincipal();
			if(_principal!=null) {
				System.out.println("CustomAuthenticationSuccessHandler >> "+_principal.getFullname()); // debug
			}
			// do something with roles?
			for (GrantedAuthority authority : authentication.getAuthorities()) {
		            System.out.println("CustomAuthenticationSuccessHandler >> "+authority.getAuthority()); // debug
		    }
			
			// Redirect user with supervisor role to dashboard page.
			if(RoleUtils.hasRole("ROLE_SUPERVISOR")) {
				path = request.getContextPath()+"/dashboard.htm";
			}
			/*
			 * if(!StringUtils.isEmpty(_principal.getRoles())) { final String[] roles =
			 * RoleUtils.parse(_principal.getRoles()); if(roles!=null) {
			 * if(RoleUtils.isSupervisor(roles)) { path =
			 * request.getContextPath()+"/dashboard.htm"; } } }
			 */
		}		
		
		final UriComponents uri = UriComponentsBuilder
					.newInstance()
					.path(path)
					.build();
	    response.sendRedirect(uri.toUriString()); 		
	}

}

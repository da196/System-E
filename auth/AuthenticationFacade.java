package com.tcra.hrms.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public String getAuthenticationToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) {
			UserAuthenticationPrincipal principal = (UserAuthenticationPrincipal) authentication.getPrincipal();
			if(principal!=null) {
				String token = StringUtils.isEmpty(principal.getToken())?"":principal.getToken();
				System.out.println("AuthenticationFacade >> token ="+token);
				return token ;
			}
		}
		return null;
	}

	@Override
	public UserAuthenticationPrincipal getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null) {
			UserAuthenticationPrincipal principal = (UserAuthenticationPrincipal) authentication.getPrincipal();
			if(principal!=null) {
				return principal ;
			}
		}
		return null;
	}

}

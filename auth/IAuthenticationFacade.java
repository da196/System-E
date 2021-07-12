package com.tcra.hrms.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;

/*
 * https://www.baeldung.com/get-user-in-spring-security
 * */
@Component
public interface IAuthenticationFacade {
	Authentication getAuthentication();
	String getAuthenticationToken();
	UserAuthenticationPrincipal getUser();
}

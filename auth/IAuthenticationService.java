package com.tcra.hrms.auth;

import org.springframework.stereotype.Component;

import com.tcra.hrms.entity.login.UserAuthentication;


@Component
public interface IAuthenticationService {
	boolean exists(String username, String password);
	UserAuthentication authenticate(String username, String password);
}

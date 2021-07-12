package com.tcra.hrms.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.tcra.hrms.configuration.AppConstants;
import com.tcra.hrms.entity.login.UserAuthentication;

@Component
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public boolean exists(String username, String password) {
		if(username!=null && password!=null) {
			return this.authenticate(username, password)!=null? true:false;
		}
		return false;
	}

	@Override
	public UserAuthentication authenticate(String username, String password) {
		try {
			if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
				final UserAuthentication user = new UserAuthentication(username,password);
				// request
				//final RestTemplate restTemplate = new RestTemplate(); 
				String url = AppConstants.BASE_URL+"/authController/authenticate";
				HttpEntity<UserAuthentication> request = new HttpEntity<UserAuthentication>(user);
				
				ResponseEntity<UserAuthentication> response = 
						restTemplate.exchange(url , HttpMethod.POST, request, UserAuthentication.class);
				if(response==null) {
					return null; // add error details - throw custom exception - to be catched in auth failure handler
				}
				int statusCode = response.getStatusCodeValue();
				System.out.println("IAuthenticationService >> Status Code-"+statusCode);
				if(statusCode!=200) {
					return null; // add error details
				}
				final UserAuthentication userAuthenticated = response.getBody();
				if(userAuthenticated==null) {
					return null; // add error details
				}
				return userAuthenticated;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.print("Exeption - "+ex.toString());
			return null;
		}
		return null;
	}

}

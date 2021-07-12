package com.tcra.hrms.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.tcra.hrms.entity.login.UserAuthentication;
import com.tcra.hrms.entity.login.UserAuthenticationPrincipal;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private IAuthenticationService authServices;
	
	public CustomAuthenticationProvider() {
	        super();
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String name = authentication.getName();
        final String password = authentication.getCredentials()!=null?authentication.getCredentials().toString():"";
        if(!StringUtils.isEmpty(name) && !StringUtils.isEmpty(password)) {
			// 1.
        	// use the credentials
            // authenticate against the third-party system - call HRMS authentication API
        	final UserAuthentication userAuthentication = authServices.authenticate(name, password);
        	// 2.
    		// parse returned result
        	// 3.
    		// update spring security principal  
        	if(userAuthentication!=null) {        		
        		// add roles
        		Collection<GrantedAuthority> grantedAuths = new ArrayList<>();
        		if(userAuthentication.getRoles()!=null && !StringUtils.isEmpty(userAuthentication.getRoles())) {
        			String[] roles = userAuthentication.getRoles().split(",");        			
        			if(roles!=null && roles.length>0) {        				
        				for(int i=0;i<roles.length;++i) {// 2, 0, 
        					String role = roles[i];
        					// enforce role with ROLE_ prefix
        					if(!role.contains("ROLE_")) {
        						role = "ROLE_"+role;
        					}
    						grantedAuths.add(new SimpleGrantedAuthority(role));
    					}
        			}
        		}
        		// user principal
        		final UserAuthenticationPrincipal principal = buildPrincipal(userAuthentication,password,grantedAuths);	            
        		System.out.println("UserAuthenticationPrincipal >> User Name = "+principal.getUsername());
        		System.out.println("UserAuthenticationPrincipal >> Full Name = "+principal.getFullname());    
        		System.out.println("UserAuthenticationPrincipal >> Authories = "+principal.getAuthorities().toString()); 
        		System.out.println("UserAuthenticationPrincipal >> Authories = "+principal.getToken());
        		System.out.println("UserAuthenticationPrincipal >> PhotoUri = "+principal.getPhoto());
        		System.out.println("UserAuthenticationPrincipal >> LeaveApproval = "+principal.getLeaveApproval());
        		System.out.println("UserAuthenticationPrincipal >> LeaveCommutationApproval = "+principal.getLeaveCommutationApproval());
        		System.out.println("UserAuthenticationPrincipal >> LeaveRecallApproval = "+principal.getLeaveRecallApproval());
        		System.out.println("UserAuthenticationPrincipal >> TrainingApproval = "+principal.getTrainingApproval());
        		System.out.println("UserAuthenticationPrincipal >> TotalApproval = "+principal.getTotalApproval());
        		// return authentication reference for session tracking
            	final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
                return auth;
        	}
        }
		return null;
	}

	// build custom principal
	private UserAuthenticationPrincipal buildPrincipal(UserAuthentication userAuthentication, String password,
			Collection<GrantedAuthority> grantedAuths) {
		if(userAuthentication!=null && grantedAuths!=null) {
			UserAuthenticationPrincipal principal = new UserAuthenticationPrincipal(userAuthentication.getEmail(),password,grantedAuths);
			// other details
			principal.setId(userAuthentication.getId());
			if(!StringUtils.isEmpty(userAuthentication.getEmail())) {
	        	principal.setUsername(userAuthentication.getEmail());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getEmail())) {
	        	principal.setEmail(userAuthentication.getEmail());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getEmpId())) {
	        	principal.setEmpId(userAuthentication.getEmpId());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getFilenumber())) {
	        	principal.setFilenumber(userAuthentication.getFilenumber());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getFullname())) {
	        	principal.setFullname(userAuthentication.getFullname());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getToken())) {
	        	principal.setToken(userAuthentication.getToken());
	        }	
	        if(!StringUtils.isEmpty(userAuthentication.getDesignation())) {
	        	principal.setDesignation(userAuthentication.getDesignation());
	        }	
	        if(!StringUtils.isEmpty(userAuthentication.getPhoto())) {
	        	principal.setPhoto(userAuthentication.getPhoto());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getLeaveApproval())) {
	        	principal.setLeaveApproval(userAuthentication.getLeaveApproval());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getLeaveCommutationApproval())) {
	        	principal.setLeaveCommutationApproval(userAuthentication.getLeaveCommutationApproval());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getLeaveRecallApproval())) {
	        	principal.setLeaveRecallApproval(userAuthentication.getLeaveRecallApproval());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getTrainingApproval())) {
	        	principal.setTrainingApproval(userAuthentication.getTrainingApproval());
	        }
	        if(!StringUtils.isEmpty(userAuthentication.getTotalApproval())) {
	        	principal.setTotalApproval(userAuthentication.getTotalApproval());
	        }
	        // is supervisor
	        if(!StringUtils.isEmpty(userAuthentication.getRoles())) {
	        	if(isSupervisor(userAuthentication.getRoles())) {
	        		principal.setSupervisor(true);
	        	}
	        }else {
	        	principal.setSupervisor(true); // debug
	        }
	        return principal;
		}
		return null;
	}

	private boolean isSupervisor(String roles) {
		return false;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

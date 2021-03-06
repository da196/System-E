package com.tcra.hrms.auth;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class RoleUtils {

	public static boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
	
	public static String[] parse(String roles) {
		if(roles!=null) {
			return roles.split(",");
		}
		return null;
	}
	
	public static boolean isSupervisor(String[] roles) {
		if(roles.length>1) {
			List<String> _roles = Arrays.asList(roles);
			if(_roles.contains("DG") || _roles.contains("HEAD")||_roles.contains("DIRECTOR")||_roles.contains("SUPERVISOR")) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNormal(String[] roles) {
		if(roles.length>1) {
			List<String> _roles = Arrays.asList(roles);
			if(_roles.contains("DG") || _roles.contains("HEAD")||_roles.contains("DIRECTOR")||_roles.contains("SUPERVISOR")) {
				return false;
			}
		}
		return true;
	}
}

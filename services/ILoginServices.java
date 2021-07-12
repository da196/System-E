package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.entity.EmployeeEntity;
import com.tcra.hrms.entity.UserEntity;;

@Component
public interface ILoginServices {

	// create login
	ResponseEntity<UserEntity> Login(UserEntity userEntity);

	ResponseEntity<EmployeeEntity> GetEmployeeGeneralInfo(String token, String email);

}

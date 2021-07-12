package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;

import com.tcra.hrms.dto.Ward;

public interface ILocationWardService {
	ResponseEntity<Ward[]> GetAll(String authToken);
	ResponseEntity<Ward[]> GetAll(String authToken,int districtId);
	ResponseEntity<Ward> Get(String authToken,int id);
	ResponseEntity<Ward> Add(String authToken,Ward ward);
	ResponseEntity<Ward> Update(String authToken,int id,Ward ward);
}

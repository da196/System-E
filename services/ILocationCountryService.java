package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.Country;

@Component
public interface ILocationCountryService {
	ResponseEntity<Country[]> GetAll(String authToken);
	ResponseEntity<Country> Get(String authToken,int id);
	ResponseEntity<Country> Add(String authToken,Country country);
	ResponseEntity<Country> Update(String authToken,int id,Country country);
}

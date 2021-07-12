package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.City;

@Component
public interface ILocationCityService {
	ResponseEntity<City[]> GetAll(String authToken);
	ResponseEntity<City[]> GetAll(String authToken,int countryId);
	ResponseEntity<City> Get(String authToken,int id);
	ResponseEntity<City> Add(String authToken,City city);
	ResponseEntity<City> Update(String authToken,int id,City city);
}

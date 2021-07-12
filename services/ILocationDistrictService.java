package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.District;

@Component
public interface ILocationDistrictService {
	ResponseEntity<District[]> GetAll(String authToken);
	ResponseEntity<District[]> GetAll(String authToken,int cityId);
	ResponseEntity<District> Get(String authToken,int id);
	ResponseEntity<District> Add(String authToken,District district);
	ResponseEntity<District> Update(String authToken,int id,District district);
}

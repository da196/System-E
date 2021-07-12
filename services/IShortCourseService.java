package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.ShortCourse;

@Component
public interface IShortCourseService {
	ResponseEntity<ShortCourse[]> GetAll(String authToken);
	ResponseEntity<ShortCourse[]> GetAll(String authToken,int employeeId);
	ResponseEntity<ShortCourse> Get(String authToken,int id);
	ResponseEntity<ShortCourse> Add(String authToken,ShortCourse course);
	ResponseEntity<ShortCourse> Update(String authToken,int id,ShortCourse course);
	ResponseEntity<Integer> Delete(String authToken,int id);
}

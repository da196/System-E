package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.EducationCourse;

@Component
public interface IEducationCourseService {
	ResponseEntity<EducationCourse[]> GetAll(String authToken);
	ResponseEntity<EducationCourse[]> GetAll(String authToken,int levelId);
	ResponseEntity<EducationCourse> Get(String authToken,int id);
	ResponseEntity<EducationCourse> Add(String authToken,EducationCourse course);
	ResponseEntity<EducationCourse> Update(String authToken,int id,EducationCourse course);
	ResponseEntity<Integer> Delete(String authToken,int id);
}

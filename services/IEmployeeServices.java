package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.Employee;

@Component
public interface IEmployeeServices {
	ResponseEntity<Employee[]> GetAll();
	ResponseEntity<Employee[]> GetAllLess();
	ResponseEntity<Employee> Create(Employee employee);
	ResponseEntity<Employee> Get(int id);
}

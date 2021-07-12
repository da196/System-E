package com.tcra.hrms.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.tcra.hrms.entity.DashboardEmployeeStatistics;

@Component
public interface IDashboardService {

	ResponseEntity<DashboardEmployeeStatistics> GetDahboardOverall(int empID, String token);

}

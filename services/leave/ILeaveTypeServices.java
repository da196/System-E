package com.tcra.hrms.services.leave;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tcra.hrms.dto.leave.LeaveType;

@Component
public interface ILeaveTypeServices {
	ResponseEntity<LeaveType[]> GetAll();
}

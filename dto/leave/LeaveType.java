package com.tcra.hrms.dto.leave;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class LeaveType implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String abbreviation;
	 private int active;
	 private int approved;
	 private int code;
	 private String description;
	 private int id;
	 private double maxdayNumber;
	 private String name;
	 private int paid;
}	

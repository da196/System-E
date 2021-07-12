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
public class LeaveBalance implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private int id;
	private int active;
	private int approved;
	private double daysremaining;
	private double daystaken;
	private double daystotal;
	private int leavetypeid;
	private String leavetypename;
}

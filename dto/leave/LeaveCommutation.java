package com.tcra.hrms.dto.leave;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class LeaveCommutation implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int empknowledge;
	private int employeeid;
	private String employeeFullName;
	private int leavetypeid;
	private String leavetypeName;
	private int numberofdaysSold;
	private String solddate;
    private double amount;
	private int active;
    private int approved;    
}	

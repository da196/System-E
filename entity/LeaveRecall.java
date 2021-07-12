package com.tcra.hrms.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class LeaveRecall implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int leaveid;
	private int leavetypeID;
	private String leavetype;	
	private int employeeID;
	private double estimatedcost;
	private String dateexpectedstart;
	private String date_expected_end;
	private String reason;

}


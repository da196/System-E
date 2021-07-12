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
public class Leave implements Serializable{
	private int id;
	private int acting;
	private String actingfullname;
	private int active;
	private String approvalcomment;
	private int approved;
	private int approvedby;
	private String approverfullname;
	private String comment;
	private String contactaddress;
	private String phoneNumber;
	private int employeeid;
	private String firstname;
	private String middlename;
	private String lastname;
	private double leaveallowance;
	private int leaveallowanceapplicable;
	private int leavetypeid;
	private String leavetypename;
	private int numberofdays;
	private int requestedby;
	private String requesterfullname;
	private String startdate;
	private String enddate;
	private int month;
	private int year;
	private int underApproval;
}

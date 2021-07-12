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
public class Recall implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int leaveid;
	private int leavetypeid;
	private String leavetype;	
	private int employeeid;
	private String employeename;
	private double numberofdays;
	private double estimatedcost;
	private int requestedbyid;
	private int requestedby;
	private String requestedbyname;
	private String startdate;
	private String enddate;
	private String approvalcomment;
	private int active;
    private int approved;
    private String dateexpectedstart;
	private String date_expected_end;
	private int empknowledge;
}

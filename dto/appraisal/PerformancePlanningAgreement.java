package com.tcra.hrms.dto.appraisal;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformancePlanningAgreement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private int id;
	private int active;
	private int approved;
	private String comments;
	private String date_created;
	private String description;
	private int employeeid;
	private int financialyearid;
	private double score;
	private double scoreannual;
	private double scorebehaviour;

}

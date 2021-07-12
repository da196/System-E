package com.tcra.hrms.dto.appraisal;

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
public class PerformanceBehaviour implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int eppaid;
	private int qualityid;
	private long ratingappraisee;
	private long ratingsupervisor;
	private long ratingagreed;
	private String datereviewed;
	
	private int employeeid;
	private int supervisorid;
	private int supervisordesignationid;

	private int active;
	private int approved;
	private String date_created;
}

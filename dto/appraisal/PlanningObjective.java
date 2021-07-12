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
public class PlanningObjective implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private int id;
	private String description;	
	private String targets;
	private String criteria;
	private double weighting;
	private int eppaid;
	private int revised;
	private String dateagreed;		
	private int active;
	private int approved;
	private String date_created;
}

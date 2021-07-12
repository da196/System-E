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
public class EmployeePerformance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String description;
    private double scoreannual;
    private double scorebehaviour;
    private double score;
    private String comments;
    private int employeeid;
    private String employeeFullName;
    private int financialyearid;
    private int approved;
    private int active;    
    private FinancialYear performanceFinancialYear;
	private List<PlanningObjective> performanceEppaObjectivelist;	
}

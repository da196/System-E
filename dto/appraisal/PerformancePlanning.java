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
public class PerformancePlanning implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PerformancePlanningAgreement performanceEppa;
	private List<PlanningObjective> performanceEppaObjectivelist;	
}

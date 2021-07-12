package com.tcra.hrms.dto.training;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrainingApprovalWorkflowStep {

	private int id;

	private int stepnumber;

	private String description;

	private int approverdesignationid;

	private String approverdesignation;

	private int approverdesignationnextid;

	private String approverdesignationnext;

	private int approverdesignationprevid;

	private String approverdesignationprev;

	private int approved;

	private int active;

}

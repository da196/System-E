package com.tcra.hrms.dto;

import java.util.List;

public class WorkflowStep {
	private List<WorkflowStepDetails> steps;
	private int workflowId;
	public List<WorkflowStepDetails> getSteps() {
		return steps;
	}
	public void setSteps(List<WorkflowStepDetails> steps) {
		this.steps = steps;
	}
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	
	
}

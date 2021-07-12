package com.tcra.hrms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.tcra.hrms.dto.WorkflowStep;
import com.tcra.hrms.dto.WorkflowStepDetails;

@Controller
public class WorkflowStepController {
	
	public int add() {
		WorkflowStep step = new WorkflowStep();
		step.setWorkflowId(1);
		// steps
		List<WorkflowStepDetails> details = new ArrayList();
		details.add(new WorkflowStepDetails());
		details.add(new WorkflowStepDetails());
		details.add(new WorkflowStepDetails());
		
		step.setSteps(details);
		// call end to add work flow step
		
		return 0;
	}

}

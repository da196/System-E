package com.tcra.hrms.entity.training;


import javax.persistence.Entity;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class HrmsTrainingApproval {

	private int id;

	private String description;

	private String approvedby;

	private int stepnumber;

	private int stepnumbernext;

	private int trainingid;

	private int stepid;

	private int approverdesignationid;

	private int workflowid;

	private int approveruserid;

	private int approved;

	private int active;

	private int status;


}

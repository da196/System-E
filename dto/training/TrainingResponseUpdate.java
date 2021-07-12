package com.tcra.hrms.dto.training;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrainingResponseUpdate {

	private int id;

	private String description;

	private String institutionaddress;

	private Double trainingcost;

	private String feestructureattachment;

	private int currencyid;

	private String currency;

	private int trainingpurposeid;

	private String trainingpurpose;

	private String name;

	private String institution;

	private int unplanned;

	private int delayed;

	private String delayedreason;

	private int unattended;

	private String unattendedreason;

	private String dateexpectedstart;

	private String dateexpectedend;

	private int financialyearid;

	private String financialyear;

	private int trainingcategoryid;

	private String trainingcategory;

	private int trainingtypeid;

	private String trainingtype;

	private String traininginitiator;

	private int traininginitiatorid;

	private int trainingsponsorid;

	private String trainingsponsor;

	private int employeeid;

	private String employeeFullName;

	private int supervisorid;

	private String supervisor;

	private int supervisordesignationid;

	private String supervisordesignation;

	private int approved;

	private int active;

	private String section;

	private String department;
}

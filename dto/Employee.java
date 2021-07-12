package com.tcra.hrms.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String cardNumber;
	private String dateofemployment;
	private int designationId;
	private String dob;
	private int employmentcategoryId;
	private String fileNumber;
	private String middleName;
	private String firstName;
	private String lastName;
	private int genderid;
	private String gender;
	private int isprobation;
	private int issupervisor;
	private int maritalstatusId;
	private int nationalityId;
	private String picture;
	private int religionId;
	private int salutationId;
	private int supervisorId;
	private int unitId;
	private String unit;
	private String designation;
	private int employmentstatusid;
	private String employmentstatus;
	private String salutation;
	private String email;
	
	//Employee profile attributes
	private String supervisor;
	private String maritalstatus;
	private String religion;
	private String nationality;
	private String dateofretirement;
	private int supervisordesignationid;
	private String passportNo;
	private String nationalId;
	private int dutystationid;
	private String dutystation;
	private String employmentcategory;

}

package com.tcra.hrms.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeBirthDetailsResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;

	private String dob;
	private String description;
	private String additionalinformation;

	private int employeeid;

	private int countryid;
	private String countryname;

	private int cityid;
	private String cityname;
	
	private int districtid;
	private String districtname;
	
	private int wardid;
	private String wardanme;

	private int attachmentid;

	private String attachmentname;
	private String uri;
	private int attachmenttypeid;

	private String attachmenttypename;
	private int approved;

	private int active;

}

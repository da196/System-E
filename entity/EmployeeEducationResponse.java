package com.tcra.hrms.entity;

import java.io.Serializable;

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
public class EmployeeEducationResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private String instituteName;
	private int instituteId;
	private String courseName;
	private int courseId;

	private String startYear;

	private String endYear;

	private int attachmentId;
	private String attachmentName;

	private int attachmentTypeId;
	private String attachmentTypeName;

	private String attachmentUri;

	private String levelName;
	private int levelId;

	private int countryId;

	private String countryname;
}

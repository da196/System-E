package com.tcra.hrms.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int code;
	private String name;
	private String abbreviation;
	private String description;
	private int educationlevelid;
	private int active;
	private int approved;	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAbbreviation() {
		return abbreviation;
	}


	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getEducationlevelid() {
		return educationlevelid;
	}



	public void setEducationlevelid(int educationlevelid) {
		this.educationlevelid = educationlevelid;
	}


	public int getActive() {
		return active;
	}



	public void setActive(int active) {
		this.active = active;
	}



	public int getApproved() {
		return approved;
	}



	public void setApproved(int approved) {
		this.approved = approved;
	}

}

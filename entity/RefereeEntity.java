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
public class RefereeEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String fullname;

	private String institution;

	private String position;

	private String phoneprimary;

	private String phonesecondary;
	
	private String email;

	private String address;
	
	private int employeeid;

	private int refereecategoryid;
	private String refereecategoryname;
	
	private int nationalityid;
	private String nationality;

	private int approved;

	private int active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhoneprimary() {
		return phoneprimary;
	}

	public void setPhoneprimary(String phoneprimary) {
		this.phoneprimary = phoneprimary;
	}

	public String getPhonesecondary() {
		return phonesecondary;
	}

	public void setPhonesecondary(String phonesecondary) {
		this.phonesecondary = phonesecondary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public int getRefereecategoryid() {
		return refereecategoryid;
	}

	public void setRefereecategoryid(int refereecategoryid) {
		this.refereecategoryid = refereecategoryid;
	}

	public String getRefereecategoryname() {
		return refereecategoryname;
	}

	public void setRefereecategoryname(String refereecategoryname) {
		this.refereecategoryname = refereecategoryname;
	}

	public int getNationalityid() {
		return nationalityid;
	}

	public void setNationalityid(int nationalityid) {
		this.nationalityid = nationalityid;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}

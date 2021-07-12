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
public class EmploymentHistoryEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDatestart() {
		return datestart;
	}

	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public String getReasonend() {
		return reasonend;
	}

	public void setReasonend(String reasonend) {
		this.reasonend = reasonend;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getEmployerphoneprimary() {
		return employerphoneprimary;
	}

	public void setEmployerphoneprimary(String employerphoneprimary) {
		this.employerphoneprimary = employerphoneprimary;
	}

	public String getEmployerphonesecondary() {
		return employerphonesecondary;
	}

	public void setEmployerphonesecondary(String employerphonesecondary) {
		this.employerphonesecondary = employerphonesecondary;
	}

	public String getEmployeremail() {
		return employeremail;
	}

	public void setEmployeremail(String employeremail) {
		this.employeremail = employeremail;
	}

	public String getEmployeraddress() {
		return employeraddress;
	}

	public void setEmployeraddress(String employeraddress) {
		this.employeraddress = employeraddress;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
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

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getSupervisorname() {
		return supervisorname;
	}

	public void setSupervisorname(String supervisorname) {
		this.supervisorname = supervisorname;
	}

	public String getSupervisortelephonenumber() {
		return supervisortelephonenumber;
	}

	public void setSupervisortelephonenumber(String supervisortelephonenumber) {
		this.supervisortelephonenumber = supervisortelephonenumber;
	}

	public String getSupervisoraddress() {
		return supervisoraddress;
	}

	public void setSupervisoraddress(String supervisoraddress) {
		this.supervisoraddress = supervisoraddress;
	}

	public String getIsyourcurrentjob() {
		return isyourcurrentjob;
	}

	public void setIsyourcurrentjob(String isyourcurrentjob) {
		this.isyourcurrentjob = isyourcurrentjob;
	}

	private int id;

	private String position;

	private String datestart;

	private String date_end;

	private String reasonend;

	private String employer;

	private String employerphoneprimary;

	private String employerphonesecondary;

	private String employeremail;

	private String employeraddress;

	private int employeeid;

	private int countryid;

	private int approved;

	private int active;

	private String duties;

	private String supervisorname;

	private String supervisortelephonenumber;

	private String supervisoraddress;

	private String isyourcurrentjob;
}

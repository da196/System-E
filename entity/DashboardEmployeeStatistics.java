package com.tcra.hrms.entity;


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
public class DashboardEmployeeStatistics {
	private int empid;
	private String departmentname;
	private int departmentfemalenumber;
	private int departmentmalenumber;
	private int departmenttotalnumber;
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public int getDepartmentfemalenumber() {
		return departmentfemalenumber;
	}
	public void setDepartmentfemalenumber(int departmentfemalenumber) {
		this.departmentfemalenumber = departmentfemalenumber;
	}
	public int getDepartmentmalenumber() {
		return departmentmalenumber;
	}
	public void setDepartmentmalenumber(int departmentmalenumber) {
		this.departmentmalenumber = departmentmalenumber;
	}
	public int getDepartmenttotalnumber() {
		return departmenttotalnumber;
	}
	public void setDepartmenttotalnumber(int departmenttotalnumber) {
		this.departmenttotalnumber = departmenttotalnumber;
	}
	
}

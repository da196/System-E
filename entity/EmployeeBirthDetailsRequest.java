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
public class EmployeeBirthDetailsRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String dob;
	private String description;
	private String additionalinformation;
	private int employeeid;
	private int countryid;
	private int cityid;
	private int districtid;
	private int wardid;
	private int attachmentid;
	private String attachmentname;
	private String uri;
	private int attachmenttypeid;
	private int approved;
	private int active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdditionalinformation() {
		return additionalinformation;
	}
	public void setAdditionalinformation(String additionalinformation) {
		this.additionalinformation = additionalinformation;
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
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public int getDistrictid() {
		return districtid;
	}
	public void setDistrictid(int districtid) {
		this.districtid = districtid;
	}
	public int getWardid() {
		return wardid;
	}
	public void setWardid(int wardid) {
		this.wardid = wardid;
	}
	public int getAttachmentid() {
		return attachmentid;
	}
	public void setAttachmentid(int attachmentid) {
		this.attachmentid = attachmentid;
	}
	public String getAttachmentname() {
		return attachmentname;
	}
	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getAttachmenttypeid() {
		return attachmenttypeid;
	}
	public void setAttachmenttypeid(int attachmenttypeid) {
		this.attachmenttypeid = attachmenttypeid;
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
	
	@Override
	public String toString() {
		return "[id="+id+",country_id="+countryid+",city_id="+cityid+",district_id="+districtid+",ward_id="+wardid+"]";
	}
}

package com.tcra.hrms.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Component
@AllArgsConstructor
@Entity
public class EmployeeRelatives implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstname;
	private String lastname;
	private String middlename;
	private String address;
	private String addresspermanent;
	private String phoneprimary;
	private String phonesecondary;
	private int relativecategoryid ;
	private int countryofbirthid ;
	private int countryofresidenceid ;
	private int employeeid ;
	private int nationalityid ;
	private int approved ;
	private int active ;
	private int Attachmentid;

	private String Attachmentname;
	private String Attachmentdescription;

	private String uri;

	private int attachmenttypeid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddresspermanent() {
		return addresspermanent;
	}

	public void setAddresspermanent(String addresspermanent) {
		this.addresspermanent = addresspermanent;
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

	public int getRelativecategoryid() {
		return relativecategoryid;
	}

	public void setRelativecategoryid(int relativecategoryid) {
		this.relativecategoryid = relativecategoryid;
	}

	public int getCountryofbirthid() {
		return countryofbirthid;
	}

	public void setCountryofbirthid(int countryofbirthid) {
		this.countryofbirthid = countryofbirthid;
	}

	public int getCountryofresidenceid() {
		return countryofresidenceid;
	}

	public void setCountryofresidenceid(int countryofresidenceid) {
		this.countryofresidenceid = countryofresidenceid;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public int getNationalityid() {
		return nationalityid;
	}

	public void setNationalityid(int nationalityid) {
		this.nationalityid = nationalityid;
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

	public int getAttachmentid() {
		return Attachmentid;
	}

	public void setAttachmentid(int attachmentid) {
		Attachmentid = attachmentid;
	}

	public String getAttachmentname() {
		return Attachmentname;
	}

	public void setAttachmentname(String attachmentname) {
		Attachmentname = attachmentname;
	}

	public String getAttachmentdescription() {
		return Attachmentdescription;
	}

	public void setAttachmentdescription(String attachmentdescription) {
		Attachmentdescription = attachmentdescription;
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
	
	
}

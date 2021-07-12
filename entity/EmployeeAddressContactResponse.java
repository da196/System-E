package com.tcra.hrms.entity;

import java.io.Serializable;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeAddressContactResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int empadressid;

	private String adressdatestart;

	private String adressdateend;

	private String adressdescriptionstart;

	private String adressdescriptionend;

	private int employeeid;
	// Adress details
	private int adressid;

	private String addressline1;

	private String addressline2;

	private String postalcode;

	private String adressdescription;

	private int addresstypeid;
	private String addresstypename;

	private int adresscityid;
	private String adresscity;

	// move to employee contact
	private int empcontactid;

	private String contactdatestart;

	private String contactdateend;

	private String contactdescriptionstart;

	private String contactdescriptionend;

	// contact details
	private int contactid;

	private String contactphoneprimary;

	private String contactphonesecondary;

	private String contactemailaddress;

	private int contacttypeid;
	private String contacttypename;

	private int approved;

	public int getEmpadressid() {
		return empadressid;
	}

	public void setEmpadressid(int empadressid) {
		this.empadressid = empadressid;
	}

	public String getAdressdatestart() {
		return adressdatestart;
	}

	public void setAdressdatestart(String adressdatestart) {
		this.adressdatestart = adressdatestart;
	}

	public String getAdressdateend() {
		return adressdateend;
	}

	public void setAdressdateend(String adressdateend) {
		this.adressdateend = adressdateend;
	}

	public String getAdressdescriptionstart() {
		return adressdescriptionstart;
	}

	public void setAdressdescriptionstart(String adressdescriptionstart) {
		this.adressdescriptionstart = adressdescriptionstart;
	}

	public String getAdressdescriptionend() {
		return adressdescriptionend;
	}

	public void setAdressdescriptionend(String adressdescriptionend) {
		this.adressdescriptionend = adressdescriptionend;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public int getAdressid() {
		return adressid;
	}

	public void setAdressid(int adressid) {
		this.adressid = adressid;
	}

	public String getAddressline1() {
		return addressline1;
	}

	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getAddressline2() {
		return addressline2;
	}

	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getAdressdescription() {
		return adressdescription;
	}

	public void setAdressdescription(String adressdescription) {
		this.adressdescription = adressdescription;
	}

	public int getAddresstypeid() {
		return addresstypeid;
	}

	public void setAddresstypeid(int addresstypeid) {
		this.addresstypeid = addresstypeid;
	}

	public String getAddresstypename() {
		return addresstypename;
	}

	public void setAddresstypename(String addresstypename) {
		this.addresstypename = addresstypename;
	}

	public int getAdresscityid() {
		return adresscityid;
	}

	public void setAdresscityid(int adresscityid) {
		this.adresscityid = adresscityid;
	}

	public String getAdresscity() {
		return adresscity;
	}

	public void setAdresscity(String adresscity) {
		this.adresscity = adresscity;
	}

	public int getEmpcontactid() {
		return empcontactid;
	}

	public void setEmpcontactid(int empcontactid) {
		this.empcontactid = empcontactid;
	}

	public String getContactdatestart() {
		return contactdatestart;
	}

	public void setContactdatestart(String contactdatestart) {
		this.contactdatestart = contactdatestart;
	}

	public String getContactdateend() {
		return contactdateend;
	}

	public void setContactdateend(String contactdateend) {
		this.contactdateend = contactdateend;
	}

	public String getContactdescriptionstart() {
		return contactdescriptionstart;
	}

	public void setContactdescriptionstart(String contactdescriptionstart) {
		this.contactdescriptionstart = contactdescriptionstart;
	}

	public String getContactdescriptionend() {
		return contactdescriptionend;
	}

	public void setContactdescriptionend(String contactdescriptionend) {
		this.contactdescriptionend = contactdescriptionend;
	}

	public int getContactid() {
		return contactid;
	}

	public void setContactid(int contactid) {
		this.contactid = contactid;
	}

	public String getContactphoneprimary() {
		return contactphoneprimary;
	}

	public void setContactphoneprimary(String contactphoneprimary) {
		this.contactphoneprimary = contactphoneprimary;
	}

	public String getContactphonesecondary() {
		return contactphonesecondary;
	}

	public void setContactphonesecondary(String contactphonesecondary) {
		this.contactphonesecondary = contactphonesecondary;
	}

	public String getContactemailaddress() {
		return contactemailaddress;
	}

	public void setContactemailaddress(String contactemailaddress) {
		this.contactemailaddress = contactemailaddress;
	}

	public int getContacttypeid() {
		return contacttypeid;
	}

	public void setContacttypeid(int contacttypeid) {
		this.contacttypeid = contacttypeid;
	}

	public String getContacttypename() {
		return contacttypename;
	}

	public void setContacttypename(String contacttypename) {
		this.contacttypename = contacttypename;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	
}

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
public class EmployeeProfessionalCertificationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String description;

	private String institution;

	private String expire;

	private String datestart;

	private String datend;

	private int employeeid;

	private int certificationcategoryid;

	private String certificationcategoryname;

	private int countryid;

	private String countryname;

	private int attachmentid;

	private String attachmentname;

	private int attachmenttypeid;

	private String attachmenttypename;
	private String uri;

	private int approved;

	private int active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getDatestart() {
		return datestart;
	}

	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}

	public String getDatend() {
		return datend;
	}

	public void setDatend(String datend) {
		this.datend = datend;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public int getCertificationcategoryid() {
		return certificationcategoryid;
	}

	public void setCertificationcategoryid(int certificationcategoryid) {
		this.certificationcategoryid = certificationcategoryid;
	}

	public String getCertificationcategoryname() {
		return certificationcategoryname;
	}

	public void setCertificationcategoryname(String certificationcategoryname) {
		this.certificationcategoryname = certificationcategoryname;
	}

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
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

	public int getAttachmenttypeid() {
		return attachmenttypeid;
	}

	public void setAttachmenttypeid(int attachmenttypeid) {
		this.attachmenttypeid = attachmenttypeid;
	}

	public String getAttachmenttypename() {
		return attachmenttypename;
	}

	public void setAttachmenttypename(String attachmenttypename) {
		this.attachmenttypename = attachmenttypename;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

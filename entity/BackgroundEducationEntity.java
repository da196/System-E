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
public class BackgroundEducationEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;

	private String datestart;

	private String datend;

	private String descriptionstart;

	private String descriptionend;
	
	private int attchmentid;
	
	private String attachmentname;

	private String attachmentdescription;

	private String uri;

	private int attachmenttypeid;
	
	private int employeeid;

	private int levelid;

	private int courseid;

	private int countryid;

	private int institutionid;

	private int approved;

	private int active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescriptionstart() {
		return descriptionstart;
	}

	public void setDescriptionstart(String descriptionstart) {
		this.descriptionstart = descriptionstart;
	}

	public String getDescriptionend() {
		return descriptionend;
	}

	public void setDescriptionend(String descriptionend) {
		this.descriptionend = descriptionend;
	}

	public int getAttchmentid() {
		return attchmentid;
	}

	public void setAttchmentid(int attchmentid) {
		this.attchmentid = attchmentid;
	}

	public String getAttachmentname() {
		return attachmentname;
	}

	public void setAttachmentname(String attachmentname) {
		this.attachmentname = attachmentname;
	}

	public String getAttachmentdescription() {
		return attachmentdescription;
	}

	public void setAttachmentdescription(String attachmentdescription) {
		this.attachmentdescription = attachmentdescription;
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

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public int getLevelid() {
		return levelid;
	}

	public void setLevelid(int levelid) {
		this.levelid = levelid;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}

	public int getInstitutionid() {
		return institutionid;
	}

	public void setInstitutionid(int institutionid) {
		this.institutionid = institutionid;
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

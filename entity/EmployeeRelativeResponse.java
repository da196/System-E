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
public class EmployeeRelativeResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;

	private String fullname;

	private String MobileNo;

	private int employeeid;

	private int relativecategoryid;
	private String firstname;
	private String middlename;
	private String lastname;

	private String relativecategoryname;
	private String phoneprimary;

	private String phonesecondary;

	private String address;

	private String addresspermanent;

	private int nationalityid;

	private String nationality;

	private int countryofbirthid;

	private String countryofbirth;

	private int countryofresidenceid;

	private String countryofresidence;

	private int approved;

	private int active;

	private int attachmentid;

	private String attachmentname;
	private String attachmentdescription;

	private String uri;

	private int attachmenttypeid;

	private String attachmenttypename;
	

}

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
public class EmploymentHistoryEntityById implements Serializable{

	private static final long serialVersionUID = 1L;

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

	private int countryid;
	private String countryname;

	private String duties;

	private String supervisorname;

	private String supervisortelephonenumber;

	private String supervisoraddress;

	private String isyourcurrentjob;

	

}
